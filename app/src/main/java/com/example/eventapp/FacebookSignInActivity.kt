package com.example.eventapp

//import com.facebook.login.widget.LoginButton
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_facebook_sign_in.*
import kotlinx.android.synthetic.main.activity_main.*


class FacebookSignInActivity : AppCompatActivity() {

    companion object {
        const val Tag: String = "FacebookAuthentication"
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var logo: ImageView
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    lateinit var callbackManager: CallbackManager
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.eventapp.R.layout.activity_facebook_sign_in)
        callbackManager = CallbackManager.Factory.create(); //gives success or failure result
        auth = FirebaseAuth.getInstance()
        login_button.setReadPermissions("email", "public_profile")


        logo = findViewById(R.id.imageView)

        // Callback registration
        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
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

    fun handleFacebookToken(token: AccessToken) {
        Log.d(Tag, "handleFacebookToken$token")
        val credentials: AuthCredential =
            FacebookAuthProvider.getCredential(token.token)
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
                            baseContext,
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
            //if (user.photoUrl != null) {
               // var photoURl: String = user.photoUrl.toString()
               // photoURl = "$photoURl?type=large"
                //Picasso.get().load(photoURl).into(imageView)
            //}
        } else {
           // textView.text = ""
            imageView.setImageResource(R.drawable.logo)
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