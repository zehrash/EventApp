package com.example.eventapp.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.eventapp.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mMenuFragmentAdapter: MainFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        //bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

/*
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    viewPager.currentItem = 0
                    //val intent = Intent(this, HomeActivity::class.java)
                    //startActivity(intent)
                }
                R.id.favourites -> {
                    viewPager.currentItem = 1
                    //val intent = Intent(this, Favourites::class.java)
                    //startActivity(intent)
                }
                R.id.search -> {
                    viewPager.currentItem = 3
                    //val intent = Intent(this, OptionsFragment::class.java)
                    //startActivity(intent)
                }
                R.id.profile -> {
                    viewPager.currentItem = 4
                    //val intent = Intent(this, ProfileActivity::class.java)
                    //startActivity(intent)
                }
            }
            false
        }


 */
        println(viewPager.currentItem)

        mMenuFragmentAdapter = MainFragmentAdapter(supportFragmentManager)
        viewPager.adapter = mMenuFragmentAdapter
        viewPager.offscreenPageLimit = 1

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                // changeTabs(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }/*
    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                var selectedFragment: Fragment? = null
                when (item.getItemId()) {
                    R.id.home -> selectedFragment = PerformersFragment()
                   // R.id.favourites -> selectedFragment = OptionsActivity()
                    R.id.search -> selectedFragment = OptionsActivity()
                    R.id.profile -> selectedFragment= OptionsFragment()
                }
                if (selectedFragment != null) {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragment_container,
                        selectedFragment
                    ).commit()
                }
                return true
            }
        }


}
*/
}
