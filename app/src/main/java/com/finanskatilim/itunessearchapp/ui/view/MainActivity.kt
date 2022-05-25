package com.finanskatilim.itunessearchapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.finanskatilim.itunessearchapp.R
import com.finanskatilim.itunessearchapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        setContentView(binding.root)
    }
}