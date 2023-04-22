package com.cepotdev.dicory.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cepotdev.dicory.R
import com.cepotdev.dicory.databinding.ActivityMainBinding
import com.cepotdev.dicory.logic.helper.SessionManager
import com.cepotdev.dicory.logic.model.ListStoryItem
import com.cepotdev.dicory.ui.adapter.StoriesAdapter
import com.cepotdev.dicory.ui.viewmodel.AuthViewModel
import com.cepotdev.dicory.ui.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                val sessionManager = SessionManager(this)
                sessionManager.removeAuthToken()
                val i = Intent(this, WelcomeActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
                finish()
                true
            }
            else -> true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = ViewModelFactory(this.applicationContext)
        val authViewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        supportActionBar?.apply {
            title = getString(R.string.main_title)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvStories.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStories.addItemDecoration(itemDecoration)

        binding.sflMain.setOnRefreshListener {
            Handler(mainLooper).postDelayed({
                binding.sflMain.isRefreshing = false
                authViewModel.showStories()
            }, 4000)
        }

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

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated when backpressed")
    override fun onBackPressed() {
        if (SessionManager(this).fetchAuthToken().isNullOrEmpty()) {
            super.onBackPressed()
        } else {
            finishAffinity()
        }
    }
}