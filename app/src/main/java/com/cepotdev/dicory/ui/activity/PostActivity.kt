package com.cepotdev.dicory.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cepotdev.dicory.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}