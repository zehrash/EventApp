package com.example.eventapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.NonNull

import androidx.appcompat.app.AppCompatActivity
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

    // private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleController: GoogleClass
    private lateinit var signOutButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //fb
        login_button.setReadPermissions("email", "public_profile")
        // auth = FirebaseAuth.getInstance()
        //
        FacebookSdk.sdkInitialize(applicationContext)



        logo = findViewById(R.id.imageView2)
        name = findViewById(R.id.textView2)
        fbController = FacebookClass(baseContext, name, logo)

        //google
        signOutButton = button2
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleController = GoogleClass(signOutButton, baseContext, googleSignInClient)

        sign_in_button.setOnClickListener {
            signIn()
        }
        signOutButton.setOnClickListener() {
            googleSignInClient.signOut()
            Toast.makeText(this, "You are logged out", Toast.LENGTH_LONG).show()
            signOutButton.visibility = View.INVISIBLE
        }

        //fb
        login_button.setOnClickListener() {
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
                    fbController.auth.signOut()
                }
            }
        }
    }


    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            super.onActivityResult(requestCode, resultCode, data)
            googleController.ActivityResult(requestCode, resultCode, data)
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    public override fun onStart() {
        super.onStart()

        fbController.auth.addAuthStateListener(authStateListener)
    }

    public override fun onStop() {
        super.onStop()
        fbController.auth.removeAuthStateListener(authStateListener)
    }
}
