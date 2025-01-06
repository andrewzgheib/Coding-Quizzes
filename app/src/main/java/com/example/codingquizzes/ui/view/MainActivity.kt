package com.example.codingquizzes.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codingquizzes.R
import com.example.codingquizzes.ui.adapter.MainAdapter

class MainActivity : AppCompatActivity(), MainAdapter.ButtonClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val buttonLabels = listOf(/*"Quizzes", */"Edit Profile")

        mainAdapter = MainAdapter(buttonLabels, this)
        recyclerView.adapter = mainAdapter
    }

    override fun onButtonClick(position: Int) {
        when (position) {
            0 -> navigateToActivity(UserProfileActivity::class.java)
/*
            1 -> navigateToActivity(QuizzesActivity::class.java)
*/
        }
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}
