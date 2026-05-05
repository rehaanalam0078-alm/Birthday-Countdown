package com.example.waittillbday

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BirthdayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birthday)

        val birthdayText = findViewById<TextView>(R.id.birthdayText)
        val button = findViewById<Button>(R.id.openMemories)

        val bounce = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        birthdayText.startAnimation(bounce)

        button.setOnClickListener {
            startActivity(Intent(this, MemoriesActivity::class.java))
        }
    }
}