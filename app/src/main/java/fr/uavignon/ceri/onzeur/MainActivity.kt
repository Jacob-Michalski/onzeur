package fr.uavignon.ceri.onzeur

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.drawable.DrawableUtils

class MainActivity : AppCompatActivity() {
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

        topSongsRecyclerView.adapter = adapterTop
        lastSongsRecyclerView.adapter = adapterLast
        val buttonMic = findViewById<ImageButton>(R.id.microphoneButton)
        buttonMic.setOnTouchListener { _, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                    buttonMic.setBackgroundResource(R.drawable.button_shape_pressed);
                }
                MotionEvent.ACTION_UP -> {
                    buttonMic.setBackgroundResource(R.drawable.button_shape);
                }
            }
            true;
        }

    }
}