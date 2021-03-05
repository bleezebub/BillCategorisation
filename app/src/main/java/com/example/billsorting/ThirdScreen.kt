package com.example.billsorting

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.grpc.Compressor
import java.io.File


class ThirdScreen : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var category: String
    private lateinit var company: String
    private lateinit var number: String
    private lateinit var rating: String
    private val GALLERY_PICK = 1
    private val IMAGE_CAPTURE = 2
    private lateinit var mStorageReference: StorageReference
    private lateinit var mRootRef: DatabaseReference
    private lateinit var finalAdapter: ArrayAdapter<String>
    var billList: ArrayList<String> = ArrayList<String>()
    private lateinit var finalSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)
        category = intent.getStringExtra("category").toString()
        company = intent.getStringExtra("company").toString()
        number = intent.getStringExtra("number").toString()
        rating = intent.getStringExtra("rating").toString()
        mStorageReference = FirebaseStorage.getInstance().getReference("Images")
        mRootRef = FirebaseDatabase.getInstance().reference
        finalSpinner = findViewById(R.id.final_page_spinner)


        finalAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, billList)
        finalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        finalSpinner.adapter = finalAdapter

        val billRef = FirebaseDatabase.getInstance().getReference("root").child("data")
            .child("$category+$company+$number+$rating")

        billRef.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    //Get map of categories in datasnapshot
                    val map = dataSnapshot.value as Map<String?, Any?>?
                    if (map != null) {
                        for (s in map.keys) {
                            billList.add(s!!)
                        }
                        billList.sort()
                        finalAdapter.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    //handle databaseError
                }
            })


        finalSpinner.onItemSelectedListener = this
    }

    fun addNewBillGalary(view: View) {
        val galleryIntent = Intent()
        galleryIntent.type = "image/*"
        galleryIntent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {


            val imageUri: Uri = data?.data ?: return
            val tsLong = System.currentTimeMillis()
            val ts = tsLong.toString()
            val pushKey: String = ts
            val filePath =
                mStorageReference.child("bill_images")
                    .child("$category + $company + $number + $rating").child(
                        "$pushKey.jpeg"
                    )
            val task = filePath.putFile(imageUri)

            val urlTask = task.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                filePath.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    Glide.with(this)
                        .load(downloadUri)
                        .into(findViewById(R.id.imageView))
                    Toast.makeText(this, "uploaded", Toast.LENGTH_SHORT).show()
                    FirebaseDatabase.getInstance().getReference("root").child("data")
                        .child("$category+$company+$number+$rating").child(ts).setValue("crap")
                } else {
                    Toast.makeText(this, "failed, please tru again", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        val pushKey = finalSpinner.selectedItem.toString()
//
//        val filePath =
//            mStorageReference.child("bill_images")
//                .child("$category + $company + $number + $rating").child(
//                    "$pushKey.jpeg"
//                )
////        Log.wtf("third_screen", filePath.downloadUrl.toString())
////
//        Toast.makeText(this, filePath.downloadUrl.toString(), Toast.LENGTH_SHORT).show()
//        Log.d("bsdkbete", filePath.downloadUrl.to)
//
//        Glide.with(this)
//            .load(filePath.downloadUrl)
//            .into(findViewById(R.id.imageView))
//
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}