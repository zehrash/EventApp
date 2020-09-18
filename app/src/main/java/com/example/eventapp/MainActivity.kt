package com.example.eventapp

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sign_in_button.setOnClickListener {
            val intent = Intent(this, GoogleSignInActivity::class.java)
            startActivity(intent)
        }

        login_button.setOnClickListener {
            val intent = Intent(this, FacebookSignInActivity::class.java)
            startActivity(intent)
        }
        login_button.setReadPermissions("email","public_profile")//idk where is this supposed to be
    }
}
