package com.example.eventapp.view.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.eventapp.view.profile.fragments.PerformersFragment
import com.example.eventapp.view.profile.fragments.VenuesFragment

@SuppressLint("WrongConstant")
class MainFragmentAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(
    fragmentManager,
    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getItem(position: Int): Fragment {
         val pos :Int= position
        Log.e("pos is ", pos.toString())
         return when (position) {
            0 -> {
                Log.e("0", "im in 0")
                PerformersFragment()
            }

            1 -> {
                Log.e("1", "im in 1")
              VenuesFragment()
            }
            3 -> {
                Log.e("3", "im in 2")
           VenuesFragment()
            }
            else -> {
                Log.e("other", "im in other")
              VenuesFragment()
            }
        }
     //   return fragment
    }

    override fun getCount(): Int {
        return 5
    }
}