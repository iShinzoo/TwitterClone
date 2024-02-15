package com.example.twitterclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class TweetActivity : AppCompatActivity() {
    private lateinit var edtTweet : EditText
    private lateinit var btnuploadTweet : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet)

        init()

        btnuploadTweet.setOnClickListener(){
            val tweet = edtTweet.text.toString()
            addTweet(tweet)
        }
    }

    private fun init(){
        edtTweet = findViewById(R.id.edtTweet)
        btnuploadTweet = findViewById(R.id.btnUploadTweet)
    }

    private fun addTweet(tweet : String){
        FirebaseDatabase.getInstance().getReference().child("users").child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listoftweets = snapshot.child("listoftweets").value as MutableList<String>
                    listoftweets.add(tweet)
                    uploadTweetList(listoftweets)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun uploadTweetList(listoftweets : List<String>){
        FirebaseDatabase.getInstance().getReference().child("users").child(Firebase.auth.uid.toString())
            .child("listoftweets").setValue(listoftweets)
        Toast.makeText(this, "Tweet Uploaded Successfully", Toast.LENGTH_SHORT).show()
    }
}