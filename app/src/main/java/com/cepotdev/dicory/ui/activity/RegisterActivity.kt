package com.cepotdev.dicory.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.cepotdev.dicory.databinding.ActivityRegisterBinding
import com.cepotdev.dicory.logic.model.UserInfo
import com.cepotdev.dicory.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()
    private val userInfo = UserInfo(
        name = "Salieri",
        email = "salieriyourboy@gmail.com",
        password = "secretcoy234"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnRegister.setOnClickListener{
            registerViewModel.userRegister(userInfo){
                if (it?.name != null){
                    Toast.makeText(this@RegisterActivity, "User has been created!", Toast.LENGTH_LONG).show()
                } else{
                    Toast.makeText(this@RegisterActivity, "Gagal!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}