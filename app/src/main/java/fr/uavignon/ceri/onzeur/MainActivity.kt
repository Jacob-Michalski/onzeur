package fr.uavignon.ceri.onzeur

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.view.MotionEvent
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.runBlocking
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), RecognitionListener, TextToSpeech.OnInitListener {

    var api: ApiSDP? = null
    var mediaPlayer: MediaPlayer? = null
    var libVLC: LibVLC? = null
    var ref : String = "null"
    var data : MutableList<Song> = ArrayList()
    var tts : TextToSpeech? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)
        api = ApiSDP()
        libVLC = LibVLC(this, ArrayList<String>().apply {
            add("--no-drop-late-frames")
            add("--no-skip-frames")
            add("--rtsp-tcp")
            add("-vvv")
        })
        mediaPlayer = MediaPlayer(libVLC)

        checkPerms()

        val topSongsRecyclerView = findViewById<RecyclerView>(R.id.topSongs)
        var lastSongsRecyclerView = findViewById<RecyclerView>(R.id.lastSongs)
        for (i in 1..3)
            data.add(Song("", "Titre$i", "Artist$i"))

        val adapterTop = RecyclerViewAdaptater(data)
        val adapterLast = RecyclerViewAdaptater(data)
        checkPerms()

        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.FRENCH)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        speechRecognizer.setRecognitionListener(this)


        topSongsRecyclerView.adapter = adapterTop
        lastSongsRecyclerView.adapter = adapterLast

        val buttonPlay = findViewById<ImageButton>(R.id.playButton)
        val buttonNext = findViewById<ImageButton>(R.id.nextButton)
        val buttonPrevious = findViewById<ImageButton>(R.id.previousButton)


        buttonPlay.setOnClickListener {
            //playSong(libVLC, mediaPlayer, api)
        }

        buttonNext.setOnClickListener {
            api?.pauseSong()
            mediaPlayer?.stop()
        }

        buttonPrevious.setOnClickListener {
            api?.resumeSong()
            libVLC = LibVLC(this, ArrayList<String>().apply {
                add("--no-drop-late-frames")
                add("--no-skip-frames")
                add("--rtsp-tcp")
                add("-vvv")
            })
            mediaPlayer = MediaPlayer(libVLC)
            val url: String = api?.IP + ref
            Media(libVLC, Uri.parse(url)).apply {
                mediaPlayer?.media = this
            }.release()
            mediaPlayer?.play()

        }

        val buttonMic = findViewById<ImageButton>(R.id.microphoneButton)
        buttonMic.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    speechRecognizer.startListening(speechRecognizerIntent)
                    buttonMic.setBackgroundResource(R.drawable.button_shape_pressed)
                    mediaPlayer?.volume = 10

                }
                MotionEvent.ACTION_UP -> {
                    buttonMic.setBackgroundResource(R.drawable.button_shape)
                    speechRecognizer.stopListening()
                    mediaPlayer?.volume = 100

                }
            }
            true
        }
    }

    private suspend fun executeCall(cmd: String, api: ApiSDP?) {
        try {
            val response = RetrofiApiC.apiService.getRequest(cmd, ref)

            if (response.isSuccessful && response.body() != null) {
                val content = response.body()
                ref = content!!.ref
                println(content.action)
                processCommand(content)
                //TODO
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Error Occurred: ${response.message()}",
                    Toast.LENGTH_LONG
                ).show()
            }

        } catch (e: Exception) {
            Toast.makeText(
                this@MainActivity,
                "Error Occurred: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private suspend fun addSong(artist: String, title: String) : Song? {
        try {
            val response = TheAudioDbApi.apiService.getTrackInfo(artist, title)

            if (response.isSuccessful && response.body() != null) {
                val content = response.body()
                if (content != null){
                    val idAlbum = content.track[0].idAlbum
                    val responseAlbum = TheAudioDbApi.apiService.getAlbumInfo("$idAlbum")
                    if (responseAlbum.isSuccessful && response.body() != null){
                        val albums = responseAlbum.body()
                        if (albums != null) {
                            data.add(0,Song(
                                albums.album[0].strAlbumThumb,
                                content.track[0].strTrack,
                                content.track[0].strArtist
                            ))
                            this.findViewById<RecyclerView>(R.id.lastSongs).adapter = RecyclerViewAdaptater(data)
                        }
                    }
                }

            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Error Occurred: ${response.message()}",
                    Toast.LENGTH_LONG
                ).show()
            }

        } catch (e: Exception) {
            Toast.makeText(
                this@MainActivity,
                "Error Occurred: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
        return null
    }

    private suspend fun playCall(artist: String, title: String) {
        try {
            val response = RetrofiApiC.apiService.getPlay(artist, title)

            if (response.isSuccessful && response.body() != null) {
                val content = response.body()
                if (content != null) {
                    ref = content
                }
                api?.playSong(artist,title)
                playSong(libVLC, mediaPlayer, api)
                addSong(artist, title)
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Error Occurred: ${response.message()}",
                    Toast.LENGTH_LONG
                ).show()
            }

        } catch (e: Exception) {
            Toast.makeText(
                this@MainActivity,
                "Error Occurred: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private suspend fun processCommand(response: ResponseAPI) {
        when (response.action) {
            "play" -> playCall(response.artist, response.title)
            "stop" -> stopSong()
            "pause" -> stopSong()
            "resume" -> resumeSong()
            "up" -> upVolume()
            "down" -> downVolume()
            "error" -> speakText("Désolé, je n'ai pas compris ou je ne connais pas cette musique.", tts!!)
        }
    }

    private fun downVolume() {
        if (mediaPlayer?.volume!! >= 0) {
            mediaPlayer!!.volume -= 10
        }
    }

    private fun upVolume() {
        if (mediaPlayer?.volume!! < 100) {
            mediaPlayer!!.volume += 10
        }
    }

    private fun speakText(text: String, tts: TextToSpeech) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun playSong(libVLC: LibVLC?, mediaPlayer: MediaPlayer?, api: ApiSDP?) {
        val url: String = api?.IP + ref
        println(url)
        //Media(libVLC, Uri.parse("rtsp://192.168.5.66:8080/test")).apply {
        Media(libVLC, Uri.parse(url)).apply {
            mediaPlayer?.media = this
        }.release()
        mediaPlayer?.play()
    }

    private fun stopSong() {
        mediaPlayer?.stop()
    }

    private fun resumeSong() {
        libVLC = LibVLC(this, ArrayList<String>().apply {
            add("--no-drop-late-frames")
            add("--no-skip-frames")
            add("--rtsp-tcp")
            add("-vvv")
        })
        mediaPlayer = MediaPlayer(libVLC)
        val url: String = api?.IP + ref
        Media(libVLC, Uri.parse(url)).apply {
            mediaPlayer?.media = this
        }.release()
        mediaPlayer?.play()
    }

    private fun checkPerms(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(this, permissions, 0)
            return true
        } else return false
    }

/*
private fun getFilePath(): String{
    val directory = this.baseContext.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
    val file = File(directory, "file.3gpp")
    return file.path
}
*/

    override fun onReadyForSpeech(p0: Bundle?) {
    }

    override fun onBeginningOfSpeech() {
    }

    override fun onRmsChanged(p0: Float) {
    }

    override fun onBufferReceived(p0: ByteArray?) {
    }

    override fun onEndOfSpeech() {
    }

    override fun onError(p0: Int) {
    }

    override fun onResults(p0: Bundle?) {
        val results: ArrayList<String> =
            p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) as ArrayList<String>
        println(results)
        runBlocking {
            executeCall(results.joinToString(), api)
        }

    }

    override fun onPartialResults(p0: Bundle?) {
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
    }

    override fun onInit(p0: Int) {

    }
}