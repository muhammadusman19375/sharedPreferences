package com.example.sharedpreferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class settingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        var settingBtn = findViewById<Button>(R.id.settings)

        settingBtn.setOnClickListener {
            startActivity(Intent(this@settingActivity,settingScreenActivity::class.java))
        }

    }
}