package com.example.billsorting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SecondScreen : AppCompatActivity() {
    lateinit var category:String
    var companyList: ArrayList<String> = ArrayList<String>()
    var numberList: ArrayList<String> = ArrayList<String>()
    var ratingList: ArrayList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)
        category = intent.getStringExtra("category").toString()
        findViewById<TextView>(R.id.title).text = category


        val companySpinner: Spinner = findViewById(R.id.company_name)
        val companyAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, companyList)
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        companySpinner.adapter = companyAdapter

        val compRef = FirebaseDatabase.getInstance().getReference("root").child("comapny")
        compRef.addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        //Get map of categories in datasnapshot
                        val map = dataSnapshot.value as Map<String?, Any?>?
                        if (map != null) {
                            for (s in map.keys) {
                                companyList.add(s!!)
                            }
                            companyList.sort()
                            companyAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        //handle databaseError
                    }
                })




        val numberSpinner: Spinner = findViewById(R.id.mobile_number)
        val numberAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numberList)
        numberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        numberSpinner.adapter = numberAdapter

        val numberRef = FirebaseDatabase.getInstance().getReference("root").child("number")
        numberRef.addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        //Get map of categories in datasnapshot
                        val map = dataSnapshot.value as Map<String?, Any?>?
                        if (map != null) {
                            for (s in map.keys) {
                                numberList.add(s!!)
                            }
                            numberList.sort()
                            numberAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        //handle databaseError
                    }
                })


        val ratingSpinner: Spinner = findViewById(R.id.rating)
        ArrayAdapter.createFromResource(
                this,
                R.array.rating_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            ratingSpinner.adapter = adapter
        }
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