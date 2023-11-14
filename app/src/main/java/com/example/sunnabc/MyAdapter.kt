package com.shanu.sunnabc

import android.app.Activity
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.shanu.sunnabc.R
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyAdapter(val context: Activity, val data: List<Data>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val img : ImageView
        val musictitle : TextView
        val play: ImageButton
        val pause: ImageButton
        val artistName: TextView


        init {
            img = itemView.findViewById(R.id.albumImg)
            musictitle = itemView.findViewById(R.id.title)
            play = itemView.findViewById(R.id.btnPlay)
            pause = itemView.findViewById(R.id.btnPause)
            artistName = itemView.findViewById(R.id.aritst)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.each_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentData = data[position]



        val mediaPlay = MediaPlayer.create(context, currentData.preview.toUri())

        holder.musictitle.text = currentData.title
        holder.artistName.text = "Artist : " + currentData.artist.name
        Picasso.get().load(currentData.album.cover).into(holder.img);

        holder.play.setOnClickListener {
            mediaPlay.start()
            holder.play.visibility = View.GONE
            holder.pause.visibility = View.VISIBLE
        }

        holder.pause.setOnClickListener {
            mediaPlay.pause()
            holder.pause.visibility = View.GONE
            holder.play.visibility = View.VISIBLE
        }
    }

}