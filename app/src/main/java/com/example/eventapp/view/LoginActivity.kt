package com.example.eventapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.eventapp.R
import com.example.eventapp.presenter.UserPresenter
import com.example.eventapp.view.search.OptionsActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapePath
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_venue.*
import kotlinx.android.synthetic.main.new_login.*


private const val RC_SIGN_IN = 7
private const val ACC_TAG = "Logged as:"

class LoginActivity : AppCompatActivity() {
    private lateinit var callbackManager: CallbackManager
    private var publisherId: String? = null
    private var publisherDisplayName: String? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_login)

       // val sharedPrefs: UserSharedPrefs = UserSharedPrefs()

        lateinit var userPresenter :UserPresenter

        val bottomAppBar = bottom_app_bar
        val fab2 = floatingActionButton
        val topEdge = BottomAppBarCutCornersTopEdge(
            bottomAppBar.fabCradleMargin,
            bottomAppBar.fabCradleRoundedCornerRadius,
            bottomAppBar.cradleVerticalOffset
        )
        val babBackground: MaterialShapeDrawable =
            bottomAppBar.background as MaterialShapeDrawable;
        //It requires 1.1.0-alpha10
        babBackground.shapeAppearanceModel = babBackground.shapeAppearanceModel
            .toBuilder()
            .setTopEdge(topEdge)
            .build()


        FacebookSdk.sdkInitialize(applicationContext)
        callbackManager = CallbackManager.Factory.create()
        auth = FirebaseAuth.getInstance()


        val fbAccessToken: AccessToken? = AccessToken.getCurrentAccessToken()
        val googleToken: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

        if (fbAccessToken != null && !fbAccessToken.isExpired && !intent.hasExtra("logout")) {
            val logoutFlag = intent.getStringExtra("flag")
            if (logoutFlag.equals("logout", true)) {

               AsyncTask.execute() {
                    userPresenter = UserPresenter.getInstance(this)
                    userPresenter.setEmail("")
                    userPresenter.setDatabase()
              //      PresenterUser.setEmail(null)
               //     PresenterUser.setDatabase()
                    userPresenter.setIsLoggedIn(false, auth.currentUser?.email!!)
                }
                FBLoginButton.performClick()
            }
        }

        if (googleToken != null && !intent.hasExtra("logout")) {
            val logoutFlag = intent.getStringExtra("flag")
            if (logoutFlag.equals("logout", true)) {
                AsyncTask.execute(){
                    userPresenter = UserPresenter.getInstance(this)
                    userPresenter.setEmail("")
                    userPresenter.setDatabase()
                   // PresenterUser.setEmail(null)
                  //  PresenterUser.setDatabase()
                    userPresenter.setIsLoggedIn(false, auth.currentUser?.email!!)
                }
                GLoginButton.performClick()
            }
        }

        if (fbAccessToken != null && !fbAccessToken.isExpired && !intent.hasExtra("logout")) { //currently logged in with fb
            var credential = FacebookAuthProvider.getCredential(fbAccessToken.token)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this@LoginActivity) { task ->
                    if (task.isSuccessful) {
                        Log.e("Firebase-FB", "signInWithCredential:success")
                        updateUI(Profile.getCurrentProfile())
                        val logoutFlag = intent.getStringExtra("flag")
                        if (!logoutFlag.equals("logout", true)) {
                            AsyncTask.execute() {
                                userPresenter=UserPresenter.getInstance(this)
                                userPresenter.setEmail(auth.currentUser?.email)
                                userPresenter.setDatabase()
                                //PresenterUser.setEmail(auth.currentUser?.email)
                                //PresenterUser.setDatabase()
                                userPresenter.addUser(auth.currentUser?.email!!, true)
                            }
                            continueButton.performClick()
                        }

                    } else {
                        Log.e("Firebase-FB", "signInWithCredential:failure", task.exception)
                        updateUI()
                    }
                }
        } else if (googleToken != null && !intent.hasExtra("logout")) { // logged in with google
            val credential = GoogleAuthProvider.getCredential(googleToken.idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this@LoginActivity) { task ->
                    if (task.isSuccessful) {
                        Log.e("Firebase-G", "signInWithCredential:success")
                        updateUI(googleToken)
                        val logoutFlag = intent.getStringExtra("flag")
                        if (!logoutFlag.equals("logout", true)) {
                            AsyncTask.execute(){
                                userPresenter=UserPresenter.getInstance(this)
                                userPresenter.setEmail(auth.currentUser?.email)
                                userPresenter.setDatabase()
                             //   PresenterUser.setEmail(auth.currentUser?.email)
                              //  PresenterUser.setDatabase()
                                userPresenter.addUser(auth.currentUser?.email!!, true)
                            }
                            continueButton.performClick()

                        }
                    } else {
                        Log.e("Firebase-G", "signInWithCredential:failure", task.exception)
                        updateUI()
                    }
                }
        } else if (intent.hasExtra("logout") && intent.getBooleanExtra("logout", false)) {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
            if (GoogleSignIn.getLastSignedInAccount(this) != null) {

                mGoogleSignInClient.signOut()
            } else {
                GraphRequest(
                    AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE
                ) {
                    LoginManager.getInstance().logOut()
                }.executeAsync()
            }
            updateUI()
        } else {
            updateUI()
        }

        continueButton.setOnClickListener {
      AsyncTask.execute(){
                userPresenter=UserPresenter.getInstance(this@LoginActivity)
                userPresenter.setEmail(auth.currentUser?.email)
                userPresenter.setDatabase()
                userPresenter.addUser(auth.currentUser?.email!!, true)
            }
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }
        GLoginButton.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

            if (GoogleSignIn.getLastSignedInAccount(this) == null) { // Google Login
                val signInIntent = mGoogleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            } else { // Google Logout
                mGoogleSignInClient.signOut()
                updateUI()
            }
        }

        FBMaskButton.setOnClickListener {
            FBLoginButton.performClick()
        }

        FBLoginButton.setOnClickListener {
            // Facebook Logout
            if (AccessToken.getCurrentAccessToken() != null) {
                GraphRequest(
                    AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE
                ) {
                    LoginManager.getInstance().logOut()
                }.executeAsync()
                updateUI()

            } else {
                // Facebook Login
                FBLoginButton.setReadPermissions("email", "public_profile")
                LoginManager.getInstance().registerCallback(callbackManager,
                    object : FacebookCallback<LoginResult> {
                        private lateinit var profileTracker: ProfileTracker

                        override fun onSuccess(loginResult: LoginResult?) {
                            if (Profile.getCurrentProfile() == null) {
                                profileTracker = object : ProfileTracker() {
                                    override fun onCurrentProfileChanged(
                                        oldProfile: Profile?,
                                        currentProfile: Profile
                                    ) {
                                        Log.e(
                                            ACC_TAG,
                                            "FB " + currentProfile.firstName + " " + currentProfile.id
                                        )
                                        profileTracker.stopTracking()
                                        val token = AccessToken.getCurrentAccessToken()
                                        var credential = FacebookAuthProvider.getCredential(token.token)
                                        auth.signInWithCredential(credential).addOnCompleteListener(this@LoginActivity) { task ->
                                                if (task.isSuccessful) {
                                                    Log.e("Firebase", "signInWithCredential:success")
                                                    updateUI(currentProfile)

                                                } else {
                                                    Log.e(
                                                        "Firebase", "signInWithCredential:failure", task.exception)
                                                    Toast.makeText(
                                                        baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                                                    updateUI()
                                                }
                                            }
                                    }
                                }

                            } else {
                                val profile = Profile.getCurrentProfile()
                                Log.v("fbLogin", profile.firstName)
                            }
                        }

                        override fun onCancel() {
                            Log.d("fbLogin", "onCancel.")
                        }

                        override fun onError(error: FacebookException) {
                            Log.d("fbLogin", "onError.")
                        }
                    }
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val account = GoogleSignIn.getLastSignedInAccount(this)
            if (account != null) {
                Log.e(ACC_TAG, "Google " + account.givenName + " " + account.id)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.e("Firebase", "signInWithCredential:success")
                        updateUI(account)
                    } else {
                        Log.e("Firebase", "signInWithCredential:failure", task.exception)
                        updateUI()
                    }
                }
                updateUI(account)
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun updateUI(account: GoogleSignInAccount) { //Google
        FBMaskButton.visibility = View.INVISIBLE
        GLoginButton.text = "Logout"
        continueButton.visibility = View.VISIBLE
        welcome_text.text =
            "Hello, " + account.givenName + ". \n Press the button below to continue."
        publisherId = account.id
        publisherDisplayName = account.displayName
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(account: Profile) { // Facebook
        FBMaskButton.text = "Logout"
        GLoginButton.visibility = View.INVISIBLE
        continueButton.visibility = View.VISIBLE
        welcome_text.text = "Hello, " + account.firstName + " Press the button below to continue."
        publisherId = account.id
        publisherDisplayName = account.firstName + " " + account.lastName

    }

    @SuppressLint("SetTextI18n")
    private fun updateUI() {
        GLoginButton.text = "Google"
        FBMaskButton.text = "Facebook"
        continueButton.visibility = View.INVISIBLE
        FBMaskButton.visibility = View.VISIBLE
        GLoginButton.visibility = View.VISIBLE
        welcome_text.text = "Log in to continue."
    }

    inner class BottomAppBarCutCornersTopEdge(
        private val fabMargin: Float,
        roundedCornerRadius: Float,
        private val cradleVerticalOffset: Float
    ) : BottomAppBarTopEdgeTreatment(fabMargin, roundedCornerRadius, cradleVerticalOffset) {
        @SuppressLint("RestrictedApi")

        override fun getEdgePath(
            length: Float,
            center: Float,
            interpolation: Float,
            shapePath: ShapePath
        ) {
            val fabDiameter = fabDiameter
            if (fabDiameter == 0f) {
                shapePath.lineTo(length, 0f)
                return
            }
            val diamondSize = fabDiameter / 2f
            val middle = center + horizontalOffset
            val verticalOffsetRatio = cradleVerticalOffset / diamondSize
            if (verticalOffsetRatio >= 1.0f) {
                shapePath.lineTo(length, 0f)
                return
            }
            shapePath.lineTo(middle - (fabMargin + diamondSize - cradleVerticalOffset), 0f)
            shapePath.lineTo(
                middle,
                (diamondSize - cradleVerticalOffset + fabMargin) * interpolation
            )
            shapePath.lineTo(middle + (fabMargin + diamondSize - cradleVerticalOffset), 0f)
            shapePath.lineTo(length, 0f)
        }

    }
}


