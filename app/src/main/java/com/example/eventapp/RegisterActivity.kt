package com.example.eventapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        FirebaseApp.initializeApp(this);

        val auth = FirebaseAuth.getInstance()

        var name: EditText = findViewById(R.id.fullname)
        val email = email.text.toString()
        val password = password.text.toString()
       // var auth = FirebaseAuth.getInstance()

        // there is no point in registering a user who has already registered
        if (auth.currentUser != null) {
            //startActivity()
            //finish()
        }

        button.setOnClickListener() {

            if (email.isEmpty()) {
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT)
                    .show() // not sure about that; test if it works???
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "Pass is required", Toast.LENGTH_SHORT)
                    .show()
            }
            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT)
                    .show()
            }
            auth.createUserWithEmailAndPassword(email, password)

            fun onComplete(task: Task<AuthResult>) {
                if (task.isSuccessful) {
                    Toast.makeText(this, "User created!", Toast.LENGTH_SHORT).show()
                    //  startActivity(this,)
                } else {
                    TODO("write case for fail")
                }

            }
        }
    }
}