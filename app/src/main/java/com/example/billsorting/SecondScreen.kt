package com.example.billsorting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class SecondScreen : AppCompatActivity() {
    lateinit var category:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)
        category = intent.getStringExtra("category").toString()
        findViewById<TextView>(R.id.title).text = category
    }

}