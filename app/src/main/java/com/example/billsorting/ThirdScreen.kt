package com.example.billsorting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class ThirdScreen : AppCompatActivity() {
    private lateinit var category: String
    private lateinit var company: String
    private lateinit var number: String
    private lateinit var rating: String
    private val GALLERY_PICK = 1
    private lateinit var mStorageReference: StorageReference
    private lateinit var mRootRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)
        category = intent.getStringExtra("category").toString()
        company = intent.getStringExtra("company").toString()
        number = intent.getStringExtra("number").toString()
        rating = intent.getStringExtra("rating").toString()
        mStorageReference = FirebaseStorage.getInstance().getReference("Images")
        mRootRef = FirebaseDatabase.getInstance().getReference();


    }

    fun addNewBillGalary(view: View) {

        val galleryIntent = Intent()
        galleryIntent.type = "image/*"
        galleryIntent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK)
    }

    fun addNewBillCamera(view: View) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK && data != null && data.data != null) {


            val imageUri: Uri = data.data ?: return
            val tsLong = System.currentTimeMillis()
            val ts = tsLong.toString()
            val pushKey: String = ts
            val filePath =
                mStorageReference.child("bill_images").child(category).child(company).child(rating)
                    .child(number).child("$pushKey.jpeg")
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
                    FirebaseDatabase.getInstance().getReference("root").child("data").child("$category+$company+$number+$rating").child(ts).setValue("crap")
                } else {
                    Toast.makeText(this, "failed, please tru again", Toast.LENGTH_SHORT).show()
                }
            }
       }


    }
}