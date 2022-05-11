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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import java.util.*

class MainActivity : AppCompatActivity(), RecognitionListener, TextToSpeech.OnInitListener {


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tts = TextToSpeech(this,this)

        val topSongsRecyclerView = findViewById<RecyclerView>(R.id.topSongs)
        val lastSongsRecyclerView = findViewById<RecyclerView>(R.id.lastSongs)
        val data = ArrayList<Song>()
        for (i in 1..20)
            data.add(Song(5, "Titre$i", "Artist$i"))

        val adapterTop = RecyclerViewAdaptater(data)
        val adapterLast = RecyclerViewAdaptater(data)

        checkPerms()

        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.FRENCH)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        speechRecognizer.setRecognitionListener(this)


        topSongsRecyclerView.adapter = adapterTop
        lastSongsRecyclerView.adapter = adapterLast

        val buttonPlay = findViewById<ImageButton>(R.id.playButton)
        val buttonNext = findViewById<ImageButton>(R.id.nextButton)
        val buttonPrevious = findViewById<ImageButton>(R.id.previousButton)
        var libVLC = LibVLC(this, ArrayList<String>().apply {
            add("--no-drop-late-frames")
            add("--no-skip-frames")
            add("--rtsp-tcp")
            add("-vvv")
        })
        var mediaPlayer = MediaPlayer(libVLC)
        val api = ApiSDP()

        buttonPlay.setOnClickListener{
            playSong(libVLC,mediaPlayer, api)
        }

        buttonNext.setOnClickListener{
            api.pauseSong()
            mediaPlayer.stop()
        }

        buttonPrevious.setOnClickListener{
            api.resumeSong()
            libVLC = LibVLC(this, ArrayList<String>().apply {
                add("--no-drop-late-frames")
                add("--no-skip-frames")
                add("--rtsp-tcp")
                add("-vvv")
            })
            mediaPlayer = MediaPlayer(libVLC)
            val url : String = api.IP + api.ref
            Media(libVLC, Uri.parse(url)).apply {
                mediaPlayer.media = this
            }.release()
            mediaPlayer.play()

        }

        val buttonMic = findViewById<ImageButton>(R.id.microphoneButton)
        buttonMic.setOnTouchListener { _, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                    if (checkPerms()){
                    } else {
                        speechRecognizer.startListening(speechRecognizerIntent)
                        buttonMic.setBackgroundResource(R.drawable.button_shape_pressed)
                        mediaPlayer.volume = 10
                    }
                }
                MotionEvent.ACTION_UP -> {
                    buttonMic.setBackgroundResource(R.drawable.button_shape)
                    speechRecognizer.stopListening()
                    mediaPlayer.volume = 100

                }
            }
            true
        }
    }

    private fun speakText(text : String, tts : TextToSpeech){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    private fun playSong(libVLC: LibVLC, mediaPlayer: MediaPlayer, api: ApiSDP) {
        if (api.ref == null) {
            api.playSong("Muse", "Feeling Good")

            while (api.ref == null) {
            }
        }
        val url : String = api.IP + api.ref
        println(url)
        //Media(libVLC, Uri.parse("rtsp://192.168.5.66:8080/test")).apply {
        Media(libVLC, Uri.parse(url)).apply {
            mediaPlayer.media = this
        }.release()
        mediaPlayer.play()
    }

    private fun checkPerms(): Boolean{
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(this, permissions, 0)
            return true
        }else return false
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
    }

        override fun onPartialResults(p0: Bundle?) {
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
    }

    override fun onInit(p0: Int) {

    }
}