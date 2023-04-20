package com.cepotdev.dicory.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cepotdev.dicory.databinding.ActivityMainBinding
import com.cepotdev.dicory.logic.model.ListStoryItem
import com.cepotdev.dicory.ui.adapter.StoriesAdapter
import com.cepotdev.dicory.ui.viewmodel.AuthViewModel
import com.cepotdev.dicory.ui.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = ViewModelFactory(this.applicationContext)
        val authViewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvStories.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStories.addItemDecoration(itemDecoration)

        if (intent.getBooleanExtra("upload_success", false)) {
            authViewModel.storyItem.observe(this) {
                showListAdapter(it)
            }
        }

        authViewModel.storyItem.observe(this) {
            showListAdapter(it)
        }

        binding.fabAdd.setOnClickListener {
            val i = Intent(this, PostActivity::class.java)
            startActivity(i)
        }

        authViewModel.isMainLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun showListAdapter(listStories: List<ListStoryItem>) {
        val adapter = StoriesAdapter(listStories)
        binding.rvStories.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbMain.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}