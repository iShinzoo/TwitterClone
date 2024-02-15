package com.example.twitterclone

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.util.UUID

class profileActivity : AppCompatActivity() {

    private lateinit var btnuploadImage : Button
    private lateinit var profileImage : CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        init()

        btnuploadImage.setOnClickListener(){
            val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent,101)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            Glide.with(this).clear(profileImage) // Assuming profileImage is the ImageView in your activity
        } catch (e: IllegalArgumentException) {
            // Ignore IllegalArgumentException as it occurs when trying to clear a load for a destroyed activity
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK){
            profileImage.setImageURI(data?.data)
            uploadProfileImage(data?.data)
        }
    }

    private fun uploadProfileImage(uri: Uri?) {
        val profileImageName = UUID.randomUUID().toString() + ".jpg"
        val storageRef = FirebaseStorage.getInstance().getReference().child("profileImage/$profileImageName")

        storageRef.putFile(uri!!).addOnSuccessListener {
            val result = it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener { downloadUri ->
                FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString())
                    .child("userProfileImage").setValue(downloadUri.toString())
                    .addOnSuccessListener {
                        // After successfully uploading to Firebase, set the image using Glide
                        Glide.with(this)
                            .load(downloadUri)
                            .into(profileImage)
                    }
            }
        }
    }


    private fun init(){
        btnuploadImage = findViewById(R.id.btnUploadImage)
        profileImage = findViewById(R.id.profileImage)

        FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val link = snapshot.child("userProfileImage").value.toString()
                    if (!link.isNullOrBlank()){
                        Glide.with(this@profileActivity)
                            .load(link)
                            .into(profileImage)
                    } else{
                        profileImage.setImageResource(R.drawable.image)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    //TODO("Not yet implemented")
                }

            })
    }
}