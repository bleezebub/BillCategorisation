package com.example.billsorting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class AddCategory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)
    }

    fun add(view: View) {
        val newCategory = findViewById<EditText>(R.id.new_category).text
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val root = FirebaseDatabase.getInstance().getReference("root")
        root.child("categories").child(newCategory.toString()).setValue(currentDate)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}