package com.example.twitterclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var toolbar : Toolbar
    private lateinit var mauth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()

        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menuu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         super.onOptionsItemSelected(item)

        when(item.itemId){
            R.id.menu_profile -> {
                // open profile page
                startActivity(
                    Intent(this,profileActivity::class.java)
                )

                Toast.makeText(this, "Edit Profile", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // signout and go back to login page
                mauth.signOut()
                startActivity(
                    Intent(this,LoginActivity::class.java)
                )
                finish()
                Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    private fun init(){
        toolbar = findViewById(R.id.toolbarLayout)
        mauth = Firebase.auth
    }
}