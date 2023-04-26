package com.cepotdev.dicory.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cepotdev.dicory.R
import com.cepotdev.dicory.databinding.ActivityDetailBinding
import com.cepotdev.dicory.logic.model.DetailStoriesResponse
import com.cepotdev.dicory.ui.viewmodel.AuthViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val keyStories = intent.getStringExtra(KEY_STORIES)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        supportActionBar?.apply {
            title = getString(R.string.detail_title)
        }

        if (keyStories != null) {
            authViewModel.getDetailStory(keyStories)
        }

        authViewModel.story.observe(this) {
            setDetail(it)
        }

        authViewModel.isDetailLoading.observe(this) {
            showLoading(it)
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

    private fun showLoading(isLoading: Boolean) {
        binding.pbDetailStories.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val KEY_STORIES = "key_stories"
    }
}