package com.cepotdev.dicory.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.cepotdev.dicory.databinding.ActivityLoginBinding
import com.cepotdev.dicory.logic.helper.SessionManager
import com.cepotdev.dicory.logic.model.LoginRequest
import com.cepotdev.dicory.ui.viewmodel.LoginViewModel
import com.cepotdev.dicory.ui.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        sessionManager = SessionManager(this)

        val viewModelFactory = ViewModelFactory(this.applicationContext)
        val loginViewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        loginViewModel.loginResponse.observe(this) { loginResponse ->
            Log.d("Readme", "before if: " + loginResponse.error.toString())
            if (loginResponse.error) {
                Toast.makeText(
                    this@LoginActivity,
                    "Cek kembali username dan password anda!",
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
            val loginRequest = LoginRequest(
                email = binding.tfLogin.text.toString(),
                password = binding.tfPassword.text.toString()
            )

            loginViewModel.userLogin(loginRequest)
        }
    }
}