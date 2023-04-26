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
import com.cepotdev.dicory.databinding.ActivityRegisterBinding
import com.cepotdev.dicory.logic.helper.emailValidation
import com.cepotdev.dicory.logic.model.UserRequest
import com.cepotdev.dicory.ui.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        playAnimation()

        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        registerViewModel.isRegisterLoading.observe(this) {
            showLoading(it)
        }

        registerViewModel.userResponse.observe(this) { userResponse ->
            if (userResponse.error) {
                Toast.makeText(
                    this@RegisterActivity,
                    getString(R.string.email_used),
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                val i = Intent(this, LoginActivity::class.java)
                Toast.makeText(this, getString(R.string.ok_login), Toast.LENGTH_SHORT).show()
                startActivity(i)
            }
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.tfRegisterEmail.text.toString()

            if (emailValidation(email)) {
                val userData = UserRequest(
                    email = binding.tfRegisterEmail.text.toString(),
                    name = binding.tfRegisterName.text.toString(),
                    password = binding.etRegisterPassword.text.toString()
                )

                registerViewModel.userRegister(userData)
            } else {
                binding.tfRegisterEmail.error = getString(R.string.invalid_email)
            }

            registerViewModel.isRegisterLoading.observe(this) {
                showLoading(it)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbRegister.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivRegister, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(500)
        val name = ObjectAnimator.ofFloat(binding.tfRegisterName, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.tfRegisterEmail, View.ALPHA, 1f).setDuration(500)
        val password =
            ObjectAnimator.ofFloat(binding.etRegisterPassword, View.ALPHA, 1f).setDuration(500)
        val btnRegister =
            ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)

        val playIt = AnimatorSet().apply {
            playTogether(name, email, password)
        }

        AnimatorSet().apply {
            playSequentially(title, playIt, btnRegister)
            start()
        }
    }
}