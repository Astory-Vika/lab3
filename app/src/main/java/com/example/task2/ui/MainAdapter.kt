package com.example.task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task2.R
import com.example.task2.data.Anime

class MainAdapter(
    var dataSet: List<Anime>, private val onDetailsClick: (Anime) -> Unit
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView
        val titleTextView: TextView
        val scoreTextView: TextView
        val descriptionTextView: TextView

        init {
            imageView = view.findViewById(R.id.itemImageView)
            titleTextView = view.findViewById(R.id.itemTitle)
            scoreTextView = view.findViewById(R.id.itemScore)
            descriptionTextView = view.findViewById(R.id.itemDescription)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.anime_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.titleTextView.text = dataSet[position].title
        viewHolder.scoreTextView.text = dataSet[position].score.toString()
        viewHolder.descriptionTextView.text = dataSet[position].synopsis

        Glide
            .with(viewHolder.imageView)
            .load(dataSet[position].images.jpg.url)
            .centerCrop()
            .into(viewHolder.imageView)
        viewHolder.itemView.setOnClickListener {
            onDetailsClick(dataSet[position])
        }
    }

    override fun getItemCount() = dataSet.size
}