package fr.uavignon.ceri.onzeur

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerViewAdaptater (private val dataSet: MutableList<Song>) : RecyclerView.Adapter<RecyclerViewAdaptater.ViewHolder>() {
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val imageView: ImageView = itemView.findViewById(R.id.songImage)
        val textTitle: TextView = itemView.findViewById(R.id.songTitle)
        val textArtist: TextView = itemView.findViewById(R.id.songArtist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.song_item_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Song = dataSet[position]
        if (Song.image != "") {
            //holder.imageView.setImageURI(Uri.parse(Song.image))
            Picasso.get().load(Song.image).into(holder.imageView);

        }
        //holder.imageView.setImageResource(Song.image)
        holder.textArtist.text = Song.artist
        holder.textTitle.text = Song.title
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}