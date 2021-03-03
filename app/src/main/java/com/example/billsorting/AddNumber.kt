package com.example.billsorting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class AddNumber : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_number)
    }

    fun addNumber(view: View) {
        val newNumber = findViewById<EditText>(R.id.new_number).text
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val root = FirebaseDatabase.getInstance().getReference("root")
        root.child("number").child(newNumber.toString()).setValue(currentDate)
        val intent = Intent(this, SecondScreen::class.java)
        startActivity(intent)
        finish()
    }
}