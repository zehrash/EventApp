package com.example.eventapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.NonNull

import androidx.appcompat.app.AppCompatActivity
import com.example.eventapp.R
import com.example.eventapp.model.register.FacebookClass
import com.example.eventapp.model.register.GoogleClass
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    companion object {
        const val Tag: String = "FacebookAuthentication"
        const val RC_SIGN_IN = 0
    }


    private var callbackManager =
        CallbackManager.Factory.create(); //gives success or failure result
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private lateinit var accessTokenTracker: AccessTokenTracker
    private lateinit var logo: ImageView
    private lateinit var name: TextView
    private lateinit var fbController: FacebookClass
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleController: GoogleClass
    private lateinit var signOutButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //fb
        login_button.setReadPermissions("email", "public_profile")
        FacebookSdk.sdkInitialize(applicationContext)
        logo = findViewById(R.id.imageView2)
        name = findViewById(R.id.textView2)
        fbController = FacebookClass(baseContext, name, logo)

        //google
        signOutButton = button2
        signOutButton.visibility = View.INVISIBLE
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleController = GoogleClass(signOutButton, baseContext, googleSignInClient)

        //google sign in button
        sign_in_button.setOnClickListener {
            login_button.visibility = View.INVISIBLE
            sign_in_button.visibility = View.INVISIBLE
            signOutButton.visibility = View.VISIBLE
            signIn()
        }
        //google sign out button
        signOutButton.setOnClickListener() {
            googleSignInClient.signOut()
            Toast.makeText(this, "You are logged out", Toast.LENGTH_LONG).show()
            signOutButton.visibility = View.INVISIBLE
            login_button.visibility = View.VISIBLE
            sign_in_button.visibility = View.VISIBLE
        }

        //fb sign in
        login_button.setOnClickListener() {
            sign_in_button.visibility = View.INVISIBLE
            signOutButton.visibility = View.INVISIBLE
            login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    Log.d(Tag, "onSuccess$loginResult")
                    fbController.handleFacebookToken(loginResult!!.accessToken)

                }

                override fun onCancel() {
                    Log.d(Tag, "onCancel")
                }

                override fun onError(exception: FacebookException) {
                    Log.d(Tag, "onError")
                }
            })
        }
        authStateListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(@NonNull firebaseAuth: FirebaseAuth) {
                val user = firebaseAuth.currentUser
                if (user != null) {
                    fbController.updateUI(user)
                } else {
                    fbController.updateUI(null)

                }
            }
        }
        accessTokenTracker = object : AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(
                oldAccessToken: AccessToken?,
                currentAccessToken: AccessToken?
            ) {
                if (currentAccessToken == null) {
                    sign_in_button.visibility = View.VISIBLE
                    fbController.auth.signOut()
                }
            }
        }
    }

    //google sign in
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    //result from google/fb sign in
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            super.onActivityResult(requestCode, resultCode, data)
            googleController.ActivityResult(requestCode, resultCode, data)
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
            super.onActivityResult(requestCode, resultCode, data)
            val intent = Intent(this@MainActivity, OptionsActivity::class.java)
            startActivity(intent)
        }
    }

    //fb add authStateListener for sign in
    public override fun onStart() {
        super.onStart()
        fbController.auth.addAuthStateListener(authStateListener)
    }

    //fb remove authStateListener after sign out
    public override fun onStop() {
        super.onStop()
        fbController.auth.removeAuthStateListener(authStateListener)
    }
}
