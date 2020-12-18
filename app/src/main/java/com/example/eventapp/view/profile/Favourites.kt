package com.example.eventapp.view.profile

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.eventapp.R
import com.example.eventapp.view.LoginActivity
import kotlinx.android.synthetic.main.activity_profile.*

class Favourites : AppCompatActivity() {

    private lateinit var mMenuViewAdapter: MenuViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        logout.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java).putExtra("flag","logout")
            startActivity(intent)
        }


        venues.setOnClickListener {
            viewPager.currentItem = 0
        }
        performers.setOnClickListener {
            viewPager.currentItem = 1
        }
        events.setOnClickListener {
            viewPager.currentItem = 2
        }
        mMenuViewAdapter = MenuViewAdapter(supportFragmentManager,this)
        viewPager.adapter = mMenuViewAdapter
        viewPager.offscreenPageLimit = 3

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                changeTabs(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    @SuppressLint("ResourceAsColor")
    private fun changeTabs(position: Int) {
        if (position == 0) {
          //  venues.background=drawable.color_selector
            performers.setBackgroundResource(R.color.primaryTextColor)
            events.setBackgroundResource(R.color.primaryTextColor)
        }
        if (position == 1) {
            venues.setBackgroundColor(R.color.primaryTextColor)
            performers.setBackgroundResource(R.color.secondaryColor)
            events.setBackgroundResource(R.color.primaryTextColor)
        }
        if (position == 2) {
            venues.setBackgroundResource(R.color.primaryTextColor)
            performers.setBackgroundResource(R.color.primaryTextColor)
            events.setBackgroundResource(R.color.secondaryColor)
        }
    }
}