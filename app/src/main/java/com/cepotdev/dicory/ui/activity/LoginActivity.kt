package com.cepotdev.dicory.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.cepotdev.dicory.R
import com.cepotdev.dicory.databinding.ActivityLoginBinding
import com.cepotdev.dicory.logic.helper.emailValidation
import com.cepotdev.dicory.logic.model.LoginRequest
import com.cepotdev.dicory.ui.viewmodel.AuthViewModel
import com.cepotdev.dicory.ui.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        playAnimation()

        val viewModelFactory = ViewModelFactory(this.applicationContext)
        val authViewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        authViewModel.loginResponse.observe(this) { loginResponse ->
            Log.d("Readme", "before if: " + loginResponse.error.toString())
            if (loginResponse.error) {
                Toast.makeText(
                    this@LoginActivity,
                    getString(R.string.check_your_input),
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
            Log.d("Readme", loginResponse.message.toString())
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.tfLoginEmail.text.toString()

            if (emailValidation(email)) {
                val loginRequest = LoginRequest(
                    email = binding.tfLoginEmail.text.toString(),
                    password = binding.etLoginPassword.text.toString()
                )

                authViewModel.userLogin(loginRequest)
            } else {
                binding.tfLoginEmail.error = getString(R.string.invalid_email)
            }

        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivLogin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.tfLoginEmail, View.ALPHA, 1f).setDuration(500)
        val password =
            ObjectAnimator.ofFloat(binding.etLoginPassword, View.ALPHA, 1f).setDuration(500)
        val btnSignIn =
            ObjectAnimator.ofFloat(binding.btnSignIn, View.ALPHA, 1f).setDuration(500)

        val playIt = AnimatorSet().apply {
            playTogether(email, password)
        }

        AnimatorSet().apply {
            playSequentially(title, playIt, btnSignIn)
            start()
        }
    }
}