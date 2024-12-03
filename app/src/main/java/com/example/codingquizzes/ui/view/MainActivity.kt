package com.example.codingquizzes.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codingquizzes.R
import com.example.codingquizzes.data.local.DatabaseProvider
import com.example.codingquizzes.data.local.UserDatabase
import com.example.codingquizzes.data.model.UserPref
import com.example.codingquizzes.ui.adapter.MainAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), MainAdapter.ButtonClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mainAdapter: MainAdapter
    private lateinit var constraint_layout: ConstraintLayout
    private lateinit var purpleBG_btn: Button
    private lateinit var whiteBG_btn: Button
    private lateinit var user_db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user_db = DatabaseProvider.getDatabase(this)

        constraint_layout = findViewById(R.id.main)
        purpleBG_btn = findViewById(R.id.purpleBG_btn)
        whiteBG_btn = findViewById(R.id.whiteBG_btn)

        recyclerView = findViewById(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val buttonLabels = listOf(/*"Quizzes", */"Edit Profile")

        mainAdapter = MainAdapter(buttonLabels, this)
        recyclerView.adapter = mainAdapter

        loadSavedBackgroundColor()
        setupBackgroundColorButtons()
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
        finish()
    }

    private fun loadSavedBackgroundColor() {
        lifecycleScope.launch {
            val savedPref = withContext(Dispatchers.IO) {
                user_db.userPrefDAO().getPreference()
            }

            savedPref?.let {
                val color = when (it.bgColor) {
                    "purple" -> R.color.purple
                    "white" -> R.color.white
                    else -> R.color.purple
                }
                constraint_layout.setBackgroundResource(color)
            }
        }
    }

    private fun setupBackgroundColorButtons() {
        purpleBG_btn.setOnClickListener {
            setBackgroundColor("purple")
        }

        whiteBG_btn.setOnClickListener {
            setBackgroundColor("white")
        }
    }

    private fun setBackgroundColor(colorName: String) {
        val colorResId = when (colorName) {
            "purple" -> R.color.purple
            "white" -> R.color.white
            else -> R.color.purple
        }

        constraint_layout.setBackgroundResource(colorResId)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val userPref = UserPref(bgColor = colorName)
                user_db.userPrefDAO().insertPreference(userPref)
            }
        }
    }
}
