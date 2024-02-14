package com.example.twitterclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var edtloginEmail : EditText
    private lateinit var edtloginpassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var btngotoSignup : Button
    private lateinit var mauth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginpage)

        init()

        btnLogin.setOnClickListener(){
            val email = edtloginEmail.text.toString()
            val password = edtloginpassword.text.toString()
            login(email, password)
        }
        btngotoSignup.setOnClickListener(){
            startActivity(
                Intent(this,SignupActivity::class.java)
            )
        }
    }
    private fun init(){
        edtloginEmail = findViewById(R.id.edtLoginEmail)
        edtloginpassword = findViewById(R.id.edtLoginpassword)
        btnLogin = findViewById(R.id.btnLogin)
        btngotoSignup = findViewById(R.id.btngotoSignup)
        mauth = Firebase.auth
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
                    Toast.makeText(this, "Some Error", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
