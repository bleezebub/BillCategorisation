package com.example.billsorting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity(), OnItemSelectedListener {

    var arrayList: ArrayList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.categories_spinner)

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this


        val ref = FirebaseDatabase.getInstance().getReference("root").child("categories")
        ref.addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        //Get map of categories in datasnapshot
                        val map = dataSnapshot.value as Map<String?, Any?>?
                        if (map != null) {
                            for (s in map.keys) {
                                arrayList.add(s!!)
                            }
                            arrayList.sort()
                            adapter.notifyDataSetChanged()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        //handle databaseError
                    }
                })




    }

    fun addCategory(view: View) {
        val intent = Intent(this, AddCategory::class.java)
        startActivity(intent)
        finish()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        category = parent?.getItemAtPosition(position).
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    fun nextStep(view: View) {
        val spinner: Spinner = findViewById(R.id.categories_spinner)
        val selectedCategory:String = spinner.selectedItem.toString()
        val intent = Intent(this, SecondScreen::class.java)
        intent.putExtra("category", selectedCategory)
        startActivity(intent)
        finish()
    }

}