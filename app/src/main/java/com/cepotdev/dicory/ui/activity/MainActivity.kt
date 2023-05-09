package com.cepotdev.dicory.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
//                val i = Intent(this, MapsActivity::class.java)
//                startActivity(i)
                Toast.makeText(this, "Maps pressed!", Toast.LENGTH_SHORT).show()
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
        getData()

        binding.fabAdd.setOnClickListener {
            val i = Intent(this, PostActivity::class.java)
            startActivity(i)
        }
    }

    private fun getData() {
        val adapter = StoriesAdapter()
        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        mainViewModel.stories.observe(this) {
            adapter.submitData(lifecycle, it)
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
}