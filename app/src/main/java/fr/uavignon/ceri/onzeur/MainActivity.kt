package fr.uavignon.ceri.onzeur

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.MotionEvent
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.drawable.DrawableUtils
import java.io.File

class MainActivity : AppCompatActivity() {

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

        var recorder: Recorder? = null

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
                        recorder = Recorder(path)
                        buttonMic.setBackgroundResource(R.drawable.button_shape_pressed);
                        Toast.makeText(this, recorder!!.startRecording(), Toast.LENGTH_SHORT).show()
                    }
                }
                MotionEvent.ACTION_UP -> {
                    buttonMic.setBackgroundResource(R.drawable.button_shape);
                    Toast.makeText(this,recorder!!.stopRecording(), Toast.LENGTH_SHORT).show()
                }
            }
            true;
        }


        val buttonPlay = findViewById<ImageButton>(R.id.playButton)
        var mediaPlayer: MediaPlayer? = null
        buttonPlay.setOnClickListener{
            mediaPlayer = MediaPlayer.create(this, Uri.fromFile(File(getFilePath())))
            mediaPlayer?.start()
        }
    }


    private fun checkPerms(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(
                android.Manifest.permission.RECORD_AUDIO,
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
}