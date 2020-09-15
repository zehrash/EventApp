package com.example.eventapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener


class GoogleSignInActivity : AppCompatActivity() {
    //val signIn: Button
    // private lateinit var mGoogleSignInClient: GoogleSignInClient
/*
   private lateinit var mGoogleSignInClient:GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        val name: TextView = findViewById(R.id.name)
        val email: TextView = findViewById(R.id.email)
        val id: TextView = findViewById(R.id.id)
        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

        val personName: String = acct?.displayName.toString()
        val personEmail: String = acct?.email.toString();
        val personId: String = acct?.id.toString()
        // Uri personPhoto = acct . getPhotoUrl ();


        name.text = personName
        email.text = personEmail
        id.text = personId
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this, OnCompleteListener<Void?> {
                // ...
            })
    }

*/
    //check for an existing signed-n user
}