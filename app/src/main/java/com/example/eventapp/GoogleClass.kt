package com.example.eventapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class GoogleClass(
    private var signOutButton: Button,
    private var context: Context,
    private var googleSignInClient: GoogleSignInClient
) {
    companion object {
        private const val RC_SIGN_IN = 0
        private var TAG = "GoogleSignInActivity"
    }

    var auth = FirebaseAuth.getInstance()
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // ...
                    Toast.makeText(context, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                    //  updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        signOutButton.visibility = View.VISIBLE
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(context.applicationContext)
        if (account != null) {
            val name: String = account.displayName.toString()
            val email: String = account.email.toString()
            // some more stuff here

            Toast.makeText(context, name + email, Toast.LENGTH_LONG).show()
        }
    }


    fun ActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = task.getResult(ApiException::class.java)
            Toast.makeText(context, "signed in successfully", Toast.LENGTH_LONG).show()
            firebaseAuthWithGoogle(account?.idToken!!)
        } catch (e: ApiException) {
            e.statusCode
            e.cause
            e.message

            Toast.makeText(context, "signed in unsuccessful", Toast.LENGTH_LONG).show()
            // updateUI(null)
        }


    }


}