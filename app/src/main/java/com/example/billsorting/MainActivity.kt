package com.example.billsorting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    //This if for Categories
    var arrayList: ArrayList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ref = FirebaseDatabase.getInstance().getReference("root").child("categories")
        ref.addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        //Get map of categories in datasnapshot
                        val map = dataSnapshot.value as Map<String?, Any?>?

                        Log.d("categories", map?.keys.toString())
                        if (map != null) {
                            for (s in map.keys) {
                                arrayList.add(s!!)
                            }
                            Log.d("categories", arrayList.size.toString())
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        //handle databaseError
                    }
                })


        val spinner:Spinner = findViewById<Spinner>(R.id.categories_spinner)

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    fun addCategory(view: View) {
        val intent = Intent(this, AddCategory::class.java)
        startActivity(intent)
        finish()
    }

    fun nextStep(view: View) {}
}