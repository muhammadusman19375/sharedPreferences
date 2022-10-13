package com.example.sharedpreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout

class settingScreenActivity : AppCompatActivity() {
    var frameLayout: FrameLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_screen)

        getSupportActionBar()!!.setTitle("Settings")
        frameLayout = findViewById(R.id.idFrameLayout)

        if(frameLayout != null){
            if(savedInstanceState != null){
                return
            }
            fragmentManager.beginTransaction().add(R.id.idFrameLayout,SettingsFragment()).commit()
        }

    }
}