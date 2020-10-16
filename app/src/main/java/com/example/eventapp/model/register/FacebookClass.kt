package com.example.eventapp.model.register

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.eventapp.R
import com.facebook.AccessToken
import com.google.firebase.auth.*
import com.squareup.picasso.Picasso

class FacebookClass(
    private var mContext: Context,
    private var textView: TextView,
    private var imageView: ImageView,

    ) {
    companion object {
        const val Tag: String = "FacebookAuthentication"
    }

    var auth = FirebaseAuth.getInstance()

    fun handleFacebookToken(token: AccessToken) {
        Log.d(Tag, "handleFacebookToken$token")
        val credentials: AuthCredential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credentials).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(Tag, "signInWithCredential:success")
                val user = auth.currentUser
                updateUI(user)

            } else {
                // If sign in fails, display a message to the user.
                Log.w(Tag, "signInWithCredential:failure", task.exception)
                Toast.makeText(mContext, "Authentication Failed", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            textView.text = user.displayName

            if (user.photoUrl != null) {
                var photoURl: String = user.photoUrl.toString()
                photoURl = "$photoURl?type=large"
                Picasso.get().load(photoURl).into(imageView)
            }
        } else {
            textView.text = ""
            imageView.setImageResource(R.drawable.logo)
        }
    }
}
