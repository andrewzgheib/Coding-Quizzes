package com.example.codingquizzes.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.codingquizzes.R
import com.example.codingquizzes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var main_ic_edit: ImageButton
    private lateinit var main_ic_code: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)

        main_ic_edit = findViewById(R.id.main_ic_edit)
        main_ic_code = findViewById(R.id.main_ic_code)

        setupIcons()
    }

    private fun setupIcons() {
        main_ic_edit.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        main_ic_code.setOnClickListener {
            val intent = Intent(this, CodeActivity::class.java)
            startActivity(intent)
        }
    }
}
