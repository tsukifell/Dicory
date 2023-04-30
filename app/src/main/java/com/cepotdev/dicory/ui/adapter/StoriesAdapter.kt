package com.cepotdev.dicory.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cepotdev.dicory.databinding.ItemStoriesBinding
import com.cepotdev.dicory.logic.helper.formattedDate
import com.cepotdev.dicory.logic.model.ListStoryItem
import com.cepotdev.dicory.ui.activity.DetailActivity

class StoriesAdapter(private val listStories: List<ListStoryItem>) :
    RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemStoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvName: TextView = binding.tvName
        val tvDescription: TextView = binding.tvDescription
        val ivPhoto: ImageView = binding.ivStoriesPhoto
        val tvDate: TextView = binding.tvDate
        val tvLat: TextView = binding.tvLat
        val tvLon: TextView = binding.tvLon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val formattedDate = formattedDate(listStories[position].createdAt.toString())
        holder.tvName.text = listStories[position].name
        holder.tvDate.text = formattedDate
        holder.tvLat.text = listStories[position].lat.toString()
        holder.tvLon.text = listStories[position].lon.toString()
        holder.tvDescription.text = listStories[position].description
        Glide.with(holder.itemView.context)
            .load(listStories[position].photoUrl)
            .into(holder.ivPhoto)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("key_stories", listStories[position].id)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listStories.size
}