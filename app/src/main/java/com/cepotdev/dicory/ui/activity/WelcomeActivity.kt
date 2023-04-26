package com.cepotdev.dicory.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.cepotdev.dicory.R
import com.cepotdev.dicory.databinding.ActivityWelcomeBinding
import com.cepotdev.dicory.logic.helper.SessionManager

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            else -> true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sessionManager = SessionManager(this)
        if (!sessionManager.fetchAuthToken().isNullOrEmpty()) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        playAnimation()

        binding.btnSignIn.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivCommunication, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.tvDicory, View.ALPHA, 1f).setDuration(500)
        val desc = ObjectAnimator.ofFloat(binding.tvWelcomeDesc, View.ALPHA, 1f).setDuration(500)
        val btnRegister =
            ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)
        val btnSignIn = ObjectAnimator.ofFloat(binding.btnSignIn, View.ALPHA, 1f).setDuration(500)

        val playIt = AnimatorSet().apply {
            playTogether(title, desc)
        }

        AnimatorSet().apply {
            playSequentially(playIt, btnSignIn, btnRegister)
            start()
        }
    }
}