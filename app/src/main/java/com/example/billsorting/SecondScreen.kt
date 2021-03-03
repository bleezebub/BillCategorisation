package com.example.billsorting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast

class SecondScreen : AppCompatActivity() {
    lateinit var category:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)
        category = intent.getStringExtra("category").toString()
        findViewById<TextView>(R.id.title).text = category
    }

    fun addCompany(view: View) {
        val intent = Intent(this, AddCompany::class.java)
        startActivity(intent)
        finish()
    }

    fun addNewNumber(view: View) {
        val intent = Intent(this, AddNumber::class.java)
        startActivity(intent)
        finish()
    }

}