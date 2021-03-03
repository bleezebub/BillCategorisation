package com.example.billsorting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    //This if for Categories
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun addCategory(view: View) {
        val intent = Intent(this, AddCategory::class.java)
        startActivity(intent)
    }
    fun nextStep(view: View) {}
}