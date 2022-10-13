package com.example.sharedpreferences

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.content.pm.SharedLibraryInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.gson.Gson
import java.util.prefs.Preferences

class MainActivity : AppCompatActivity() {
    var switch: Switch? = null
    var parent_layout: ConstraintLayout? = null
    var USER_KEY: String = "user_key"
    var FILE_NAME: String = "myDemoFile"
    var EMAIL_KEY: String = "email_key"
    var COLOR_KEY: String = "color"
    var editText_name:EditText? = null
    var editText_age:EditText? = null
    var btnRemove: Button? = null
    private lateinit var mPrefListener: SharedPreferences.OnSharedPreferenceChangeListener

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        editText_name = findViewById(R.id.editText1)
        editText_age = findViewById(R.id.editText2)
        var button = findViewById<Button>(R.id.button)
        var skipBtn = findViewById<Button>(R.id.skipBtn)
        var saveToPref = findViewById<Button>(R.id.savetopref)
        var readTopref = findViewById<Button>(R.id.readtopref)
        switch = findViewById(R.id.switch1)
        parent_layout = findViewById(R.id.parent_layout)
        btnRemove = findViewById(R.id.btnRemove)

        var settingPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)


        mPrefListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if(key.equals("key_upload_quality")){
                var value:Int = sharedPreferences.getInt("key_upload_quality",0)
                Toast.makeText(this@MainActivity," "+value,Toast.LENGTH_SHORT).show()
            }
        }
        settingPref.registerOnSharedPreferenceChangeListener(mPrefListener)


        skipBtn.setOnClickListener {

        }

        button.setOnClickListener(View.OnClickListener {
//writing the value into file.........................................
            var editor: SharedPreferences.Editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit()
            editor.putString(EMAIL_KEY,editText_name!!.text.toString())
            editor.apply()
            Toast.makeText(this@MainActivity,"Email written into file",Toast.LENGTH_SHORT).show()
        })

        switch!!.setOnCheckedChangeListener { buttonView, isChecked ->
//writing the boolean value into file..................................
            var sharedPreferences: SharedPreferences = getPreferences(MODE_PRIVATE)
            var editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(COLOR_KEY, isChecked)
            editor.apply()
            if(isChecked){
                parent_layout!!.setBackgroundColor(Color.GREEN)
            }
            else{
                parent_layout!!.setBackgroundColor(Color.WHITE)
            }

        }

        saveToPref.setOnClickListener {

            //Convert data into Json formate and then store in SP file
            var username: String = editText_name!!.text.toString()
            var age: Int = Integer.valueOf(editText_age!!.text.toString())

            var user: User = User(username,age)
            var gson: Gson = Gson()
            var jsonStr = gson.toJson(user, User::class.java)

            var pref: SharedPreferences = getSharedPreferences("MyFile", MODE_PRIVATE)
            var editor: SharedPreferences.Editor = pref.edit()
            editor.putString(USER_KEY,jsonStr)
            editor.apply()

            Toast.makeText(this@MainActivity,"User written...",Toast.LENGTH_SHORT).show()
        }

        readTopref.setOnClickListener {

            //read data from SP file in gson and then desearilize it from gson to json and read data
            var sharedPreferences: SharedPreferences = getSharedPreferences("MyFile", MODE_PRIVATE)
            var value: String? = sharedPreferences.getString(USER_KEY,"N/A")

            var gson: Gson = Gson()
            var user: User = gson.fromJson(value,User::class.java)



            Toast.makeText(this@MainActivity,user.username +" "+user.age,Toast.LENGTH_SHORT).show()


        }

        btnRemove!!.setOnClickListener(View.OnClickListener {

            // delete values from default shared preferences file
            var sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            var editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            Toast.makeText(this@MainActivity,"Values deleted",Toast.LENGTH_SHORT).show()
        })

    }

    override fun onStart() {
        super.onStart()
//Readnig the boolean value from file..............................
        var preferences: SharedPreferences = getPreferences(MODE_PRIVATE)
        var check: Boolean = preferences.getBoolean(COLOR_KEY,false)

        when(check){
            true -> parent_layout!!.setBackgroundColor(Color.GREEN)
            false -> parent_layout!!.setBackgroundColor(Color.WHITE)
        }
        switch!!.isChecked = check

    }
}