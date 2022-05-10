package fr.uavignon.ceri.onzeur

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.MotionEvent
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import java.io.File
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity(), RecognitionListener {


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topSongsRecyclerView = findViewById<RecyclerView>(R.id.topSongs)
        val lastSongsRecyclerView = findViewById<RecyclerView>(R.id.lastSongs)
        val data = ArrayList<Song>()
        for (i in 1..20)
            data.add(Song(5, "Titre$i", "Artist$i"))

        val adapterTop = RecyclerViewAdaptater(data)
        val adapterLast = RecyclerViewAdaptater(data)

        checkPerms()

        var speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        var speechRecognizerIntent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.FRENCH)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        speechRecognizer.setRecognitionListener(this)

        var path = getFilePath()


        topSongsRecyclerView.adapter = adapterTop
        lastSongsRecyclerView.adapter = adapterLast

        val buttonMic = findViewById<ImageButton>(R.id.microphoneButton)
        buttonMic.setOnTouchListener { _, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        ActivityCompat.requestPermissions(this, permissions,0)
                    } else {
                        speechRecognizer.startListening(speechRecognizerIntent)
                        //recorder = Recorder(path)
                        buttonMic.setBackgroundResource(R.drawable.button_shape_pressed);
                        //Toast.makeText(this, recorder!!.startRecording(), Toast.LENGTH_SHORT).show()
                    }
                }
                MotionEvent.ACTION_UP -> {
                    buttonMic.setBackgroundResource(R.drawable.button_shape)
                    speechRecognizer.stopListening()

                    //Toast.makeText(this,recorder!!.stopRecording(), Toast.LENGTH_SHORT).show()
                }
            }
            true;
        }

        val buttonPlay = findViewById<ImageButton>(R.id.playButton)
        var libVLC : LibVLC? = null
        libVLC = LibVLC(this, ArrayList<String>().apply {
            add("--no-drop-late-frames")
            add("--no-skip-frames")
            add("--rtsp-tcp")
            add("-vvv")
        })
        var mediaPlayer = MediaPlayer(libVLC)

        buttonPlay.setOnClickListener{
            Media(libVLC, Uri.parse("rtsp://192.168.5.66:8080/test")).apply {
                mediaPlayer.media = this
            }.release()
            mediaPlayer.play()
        }
    }

    private fun checkPerms(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(this, permissions, 0)
        }
    }

    private fun getFilePath(): String{
        var directory = this.baseContext.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        var file = File(directory, "file.3gpp")
        return file.path
    }

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
}