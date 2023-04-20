package com.cepotdev.dicory.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.cepotdev.dicory.R
import com.cepotdev.dicory.databinding.ActivityRegisterBinding
import com.cepotdev.dicory.logic.helper.emailValidation
import com.cepotdev.dicory.logic.model.UserRequest
import com.cepotdev.dicory.ui.viewmodel.AuthViewModel
import com.cepotdev.dicory.ui.viewmodel.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val viewModelFactory = ViewModelFactory(this.applicationContext)
        val authViewModel =
            ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        authViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        authViewModel.userResponse.observe(this) { userResponse ->
            Log.d("Readme", "before if: " + userResponse.error.toString())
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
            Log.d("Readme", userResponse.message)
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.tfRegisterEmail.text.toString()

            if (emailValidation(email)) {
                val userData = UserRequest(
                    email = binding.tfRegisterEmail.text.toString(),
                    name = binding.tfRegisterName.text.toString(),
                    password = binding.etRegisterPassword.text.toString()
                )

                authViewModel.userRegister(userData)
            } else {
                binding.tfRegisterEmail.error = getString(R.string.invalid_email)
            }

            authViewModel.isLoading.observe(this) {
                showLoading(it)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}