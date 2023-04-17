package com.cepotdev.dicory.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.cepotdev.dicory.databinding.ActivityRegisterBinding
import com.cepotdev.dicory.logic.model.UserRequest
import com.cepotdev.dicory.ui.viewmodel.LoginViewModel
import com.cepotdev.dicory.ui.viewmodel.RegisterViewModel
import com.cepotdev.dicory.ui.viewmodel.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val viewModelFactory = ViewModelFactory(this.applicationContext)
        val registerViewModel =
            ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        registerViewModel.userResponse.observe(this) { userResponse ->
            Log.d("Readme", "before if: " + userResponse.error.toString())
            if (userResponse.error) {
                Toast.makeText(this@RegisterActivity, "Email telah terpakai!", Toast.LENGTH_LONG)
                    .show()
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }
            Log.d("Readme", userResponse.message.toString())
        }

        binding.btnRegister.setOnClickListener {
            val userData = UserRequest(
                email = binding.tfLogin.text.toString(),
                name = binding.tfUsername.text.toString(),
                password = binding.tfPassword.text.toString()
            )

            registerViewModel.userRegister(userData)

            registerViewModel.isLoading.observe(this) {
                showLoading(it)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}