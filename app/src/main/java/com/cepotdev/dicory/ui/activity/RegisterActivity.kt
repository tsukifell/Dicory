package com.cepotdev.dicory.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.cepotdev.dicory.databinding.ActivityRegisterBinding
import com.cepotdev.dicory.logic.model.UserInfo
import com.cepotdev.dicory.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnRegister.setOnClickListener {
            val userData = UserInfo(
                email = binding.tfLogin.text.toString(),
                name = binding.tfUsername.text.toString(),
                password = binding.tfPassword.text.toString()
            )
            registerViewModel.userRegister(userData)
            val i = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(i)
        }
    }
}