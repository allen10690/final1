package com.example.final1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val settingbtn : Button = findViewById(R.id.button3)
        //val text :TextView = findViewById(R.id.textView)
        settingbtn.setOnClickListener{
            val intent = Intent(this, Setting_and_Privacy::class.java)
            startActivity(intent)
        }
    }
}