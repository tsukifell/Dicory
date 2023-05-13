package com.cepotdev.dicory.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.cepotdev.dicory.R
import com.cepotdev.dicory.databinding.ActivityLoginBinding
import com.cepotdev.dicory.logic.helper.emailValidation
import com.cepotdev.dicory.logic.model.LoginRequest
import com.cepotdev.dicory.ui.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        playAnimation()
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        loginViewModel.loginResponse.observe(this) { loginResponse ->
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
        }

        loginViewModel.isLoginLoading.observe(this) {
            showLoading(it)
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.tfLoginEmail.text.toString()

            if (emailValidation(email)) {
                val loginRequest = LoginRequest(
                    email = binding.tfLoginEmail.text.toString(),
                    password = binding.etLoginPassword.text.toString()
                )

                loginViewModel.userLogin(loginRequest)
            } else {
                binding.tfLoginEmail.error = getString(R.string.invalid_email)
            }

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
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