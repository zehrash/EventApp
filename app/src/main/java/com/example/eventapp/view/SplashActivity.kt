package com.example.eventapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.eventapp.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    lateinit var top: ImageView
    lateinit var bottom: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        top = iv_top
        bottom = iv_bottom

        val animation1 = AnimationUtils.loadAnimation(this, R.anim.top_wave)
        top.startAnimation(animation1)

        val animation2 = AnimationUtils.loadAnimation(this, R.anim.bottom_wave)
        bottom.startAnimation(animation2)

        Handler().postDelayed(object : Runnable {
            override fun run() {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                finish()

            }
        }, 4000)
    }
}