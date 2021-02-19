package com.artemkudryavtsev.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.artemkudryavtsev.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.dailyNewsTab.setOnClickListener {
            it.findNavController().navigate(R.id.dailyNewsFragment)
        }

        binding.bookmarksTab.setOnClickListener {
            it.findNavController().navigate(R.id.bookmarksFragment)
        }

        binding.settingsTab.setOnClickListener {
            it.findNavController().navigate(R.id.settingsFragment)
        }
    }
}
