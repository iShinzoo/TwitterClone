package com.example.twitterclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.twitterclone.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var edtloginEmail : EditText
    private lateinit var edtloginpassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var mauth: FirebaseAuth
    private lateinit var btnSignup : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginpage)

        init()

        btnLogin.setOnClickListener(){
            val email = edtloginEmail.text.toString()
            val password = edtloginpassword.text.toString()
            login(email, password)
        }

        btnSignup.setOnClickListener(){
            val email = edtloginEmail.text.toString()
            val password = edtloginpassword.text.toString()
            SignUp(email,password)
        }

        if (mauth.currentUser != null){
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun init(){
        edtloginEmail = findViewById(R.id.edtLoginEmail)
        edtloginpassword = findViewById(R.id.edtLoginpassword)
        btnLogin = findViewById(R.id.btnLogin)
        mauth = Firebase.auth
        btnSignup = findViewById(R.id.btnSignup)
    }

    private fun login(email : String, password : String){
        mauth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(
                        Intent(this,HomeActivity::class.java)
                    )
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Create an Account First", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun SignUp(email : String, password : String){
        mauth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val listofFollowings = mutableListOf<String>()
                    listofFollowings.add("")
                    val listofTweets = mutableListOf<String>()
                    listofTweets.add("")

                    // add user to the firebase database
                    val user = User(
                        userEmail = "",
                        userName = "",
                        userProfileImage = "",
                        listOffollowings = listofFollowings,
                        listoftweets = listofTweets,
                        uid = mauth.uid.toString()
                    )

                    addUserToDatabase(user)

                    // Sign in success, update UI with the signed-in user's information

                    Toast.makeText(this, "Signed Up Successfully", Toast.LENGTH_SHORT).show()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Some Error occured", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(user: User){
        Firebase.database.getReference("users").child(user.uid).setValue(user)
    }
}
