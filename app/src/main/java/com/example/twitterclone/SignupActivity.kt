package com.example.twitterclone

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {

    private lateinit var edtsignupEmail : EditText
    private lateinit var edtsignuppassword : EditText
    private lateinit var btnSignup : Button
    private lateinit var mauth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page)

        init()

        btnSignup.setOnClickListener(){
            val email = edtsignupEmail.text.toString()
            val password = edtsignuppassword.text.toString()
            SignUp(email,password)
        }

    }
    private fun init(){
        edtsignupEmail = findViewById(R.id.edtsignupEmail)
        edtsignuppassword = findViewById(R.id.edtsignuppassword)
        btnSignup = findViewById(R.id.btnSignup)
        mauth = Firebase.auth
    }
    private fun SignUp(email : String, password : String){
        mauth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(
                        Intent(this,LoginActivity::class.java)
                    )
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Some Error occured", Toast.LENGTH_SHORT).show()
                }
            }
    }
}