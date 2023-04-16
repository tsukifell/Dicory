package com.cepotdev.dicory.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.cepotdev.dicory.databinding.ActivityDetailBinding
import com.cepotdev.dicory.logic.model.DetailStoriesResponse
import com.cepotdev.dicory.viewmodel.MainViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val keyStories = intent.getStringExtra("key_stories")
        val mainViewModel = MainViewModel(context = applicationContext)

        if (keyStories != null) {
            mainViewModel.getDetailStory(keyStories)
        }

        mainViewModel.story.observe(this) {
            setDetail(it)
        }
    }

    private fun setDetail(stories: DetailStoriesResponse?) {
        Log.d("Detail activity", "setDetail: $stories")
        if (stories != null) {
            binding.tvDetailName.text = stories.story?.name
            binding.tvDetailDescription.text = stories.story?.description
            Glide.with(this)
                .load(stories.story?.photoUrl)
                .into(binding.ivDetailStories)
        }
    }
}