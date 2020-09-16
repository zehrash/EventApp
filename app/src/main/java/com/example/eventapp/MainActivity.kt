package com.example.eventapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_google_sign_in.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    // private var mAuth: FirebaseAuth? = null
    private lateinit var auth: FirebaseAuth

    //private val RC_SIGN_IN = 1
    //private lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register.setOnClickListener(){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }




        @Override
        fun onStart() {
            super.onStart()
            // Check if user is signed in (non-null) and update UI accordingly.
            val currentUser = auth.currentUser
            //updateUI(currentUser)
        }
/*
        fun createAccount(){
            auth.createUserWithEmailAndPassword(email.toString(), password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override

                     fun onComplete( task: Task<AuthResult>) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            val user = auth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
        }
    }


        private fun signIn() {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(
                signInIntent, RC_SIGN_IN
            )

        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == RC_SIGN_IN) {
                val task =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
        }

        private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
            try {
                val account = completedTask.getResult(
                    ApiException::class.java
                )
                val googleId = account?.id ?: ""
                Log.i("Google ID", googleId)

                val googleFirstName = account?.givenName ?: ""

                Log.i("Google First Name", googleFirstName)

                val googleLastName = account?.familyName ?: ""
                Log.i("Google Last Name", googleLastName)

                val googleEmail = account?.email ?: ""
                Log.i("Google Email", googleEmail-)

            } catch (e: ApiException) {
                Log.e(
                    "failed code=", e.statusCode.toString()
                )
            }
        }

        private fun signOut() {
            mGoogleSignInClient.signOut()
                .addOnCompleteListener(this) {
                    // Update your UI here
                }
        }
}

 */
    }
}
