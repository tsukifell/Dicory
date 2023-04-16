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
import com.cepotdev.dicory.logic.model.ListStoryItem
import com.cepotdev.dicory.ui.activity.DetailActivity

class StoriesAdapter(private val listStories: List<ListStoryItem>) :
    RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvDecscription: TextView = view.findViewById(R.id.tv_description)
        val ivPhoto: ImageView = view.findViewById(R.id.iv_stories_photo)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(R.layout.item_stories, viewGroup, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = listStories[position].name
        holder.tvDecscription.text = listStories[position].description
        Glide.with(holder.itemView.context)
            .load(listStories[position].photoUrl)
            .into(holder.ivPhoto)

        holder.itemView.setOnClickListener{
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("key_stories", listStories[position].id)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listStories.size
}