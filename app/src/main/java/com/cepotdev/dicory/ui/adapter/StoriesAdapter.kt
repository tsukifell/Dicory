package com.cepotdev.dicory.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cepotdev.dicory.R
import com.cepotdev.dicory.databinding.ItemStoriesBinding
import com.cepotdev.dicory.logic.model.ListStoryItem
import com.cepotdev.dicory.ui.activity.DetailActivity

class StoriesAdapter(private val listStories: List<ListStoryItem>) :
    RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemStoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvName: TextView = binding.tvName
        val tvDecscription: TextView = binding.tvDescription
        val ivPhoto: ImageView = binding.ivStoriesPhoto
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = listStories[position].name
        holder.tvDecscription.text = listStories[position].description
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