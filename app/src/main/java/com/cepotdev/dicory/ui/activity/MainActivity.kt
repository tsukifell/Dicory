package com.cepotdev.dicory.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.cepotdev.dicory.R
import com.cepotdev.dicory.databinding.ActivityMainBinding
import com.cepotdev.dicory.logic.helper.SessionManager
import com.cepotdev.dicory.ui.adapter.LoadingStateAdapter
import com.cepotdev.dicory.ui.adapter.StoriesAdapter
import com.cepotdev.dicory.ui.viewmodel.MainViewModel
import com.cepotdev.dicory.ui.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(this)
    }
    private val storiesAdapter: StoriesAdapter by lazy { StoriesAdapter() }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                val i = Intent(this, MapsActivity::class.java)
                startActivity(i)
                true
            }

            R.id.menu2 -> {
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

        supportActionBar?.apply {
            title = getString(R.string.main_title)
        }

        binding.rvStories.layoutManager = LinearLayoutManager(this)

        setupRecyclerView()

        binding.fabAdd.setOnClickListener {
            val i = Intent(this, PostActivity::class.java)
            startActivity(i)
        }
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

    private fun setupRecyclerView() {
        binding.rvStories.adapter = storiesAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { storiesAdapter.retry() }
        )

        mainViewModel.isMainLoading.observe(this) { isLoading ->
            binding.pbMain.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        storiesAdapter.addLoadStateListener { loadState ->
            val isMainLoading = loadState.refresh is LoadState.Loading
            mainViewModel.setMainLoading(isMainLoading)
        }

        mainViewModel.stories.observe(this) { pagingData ->
            storiesAdapter.submitData(lifecycle, pagingData)
        }
    }
}