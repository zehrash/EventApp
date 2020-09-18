package com.example.eventapp

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.example.eventapp.R.layout.activity_facebook_sign_in
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseError
import com.google.firebase.auth.*
import com.squareup.picasso.Picasso


import kotlinx.android.synthetic.main.activity_facebook_sign_in.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*


class FacebookSignInActivity : AppCompatActivity() {

    companion object {
        private const val Tag: String = "FacebookAuthentication"
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var logo: ImageView
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_facebook_sign_in)


        callbackManager = CallbackManager.Factory.create(); //gives success or failure result
        auth = FirebaseAuth.getInstance()
        val textView: TextView = findViewById(com.example.eventapp.R.id.text_id)
        val logo: ImageView = findViewById(com.example.eventapp.R.id.imageView)



        val EMAIL = "email"

        val loginButton = findViewById(com.example.eventapp.R.id.login_button) as LoginButton
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult>)
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                Log.d(Tag, "onSuccess$loginResult")
                handleFacebookToken(loginResult!!.accessToken)
            }

            override fun onCancel() {
                Log.d(Tag, "onCancel")
            }

            override fun onError(exception: FacebookException) {
                Log.d(Tag, "onError")
            }
        })
    }

    fun handleFacebookToken(getAccessToken: AccessToken) {
        Log.d(Tag, "handleFacebookToken$getAccessToken")
        val credentials: AuthCredential =
            FacebookAuthProvider.getCredential(getAccessToken.toString())
        auth.signInWithCredential(credentials)
            .addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(Tag, "signInWithCredential:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(Tag, "signInWithCredential:failure", task.exception)
                        Toast.makeText(
                            this@FacebookSignInActivity,
                            "Authentication Failed",
                            Toast.LENGTH_SHORT
                        ).show()
                        //  updateUI(null)
                    }
                }
            })
        authStateListener = FirebaseAuth.AuthStateListener {
            fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
                val user = firebaseAuth.currentUser
                if (user != null) {
                    updateUI(user)
                } else {
                    updateUI(null)

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            textView.text = user.displayName
            if (user.photoUrl != null) {
                var photoURl: String = user.photoUrl.toString()
                photoURl = "$photoURl?type=large"
                Picasso.get().load(photoURl).into(logo)
            }
        } else {
            textView.text = ""
            logo.setImageResource(com.example.eventapp.R.drawable.logo)//add image to drawable
        }
    }

    public override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authStateListener)
    }

    public override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authStateListener)
    }

}