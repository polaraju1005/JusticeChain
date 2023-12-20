package com.example.justicechain

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btnStart:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.btGetStarted)

        btnStart.setOnClickListener {
            startActivity(Intent(this@MainActivity,ActivityLogin::class.java))
            finish()
        }
    }

}