package fr.uavignon.ceri.onzeur

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.RecyclerView

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

    }
}