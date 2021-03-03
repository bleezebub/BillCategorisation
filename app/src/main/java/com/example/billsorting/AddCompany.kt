package com.example.billsorting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class AddCompany : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_company)
    }

    fun add(view: View) {
        val newCompany = findViewById<EditText>(R.id.new_company).text
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val root = FirebaseDatabase.getInstance().getReference("root")
        root.child("comapny").child(newCompany.toString()).setValue(currentDate)
        val intent = Intent(this, SecondScreen::class.java)
        startActivity(intent)
        finish()
    }
}