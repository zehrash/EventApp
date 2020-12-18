package com.example.eventapp.view.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.eventapp.database.model.Venue
import com.example.eventapp.presenter.UserPresenter
import com.example.eventapp.view.profile.fragments.EventsFragment
import com.example.eventapp.view.profile.fragments.PerformersFragment
import com.example.eventapp.view.profile.fragments.VenuesFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.math.log


class MenuViewAdapter(
    fragManager: FragmentManager, context: Context,

    ) :
    FragmentPagerAdapter(fragManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT),
    CoroutineScope {
    val context = context
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                /*
                *Request to database to return all favourite venue
                *Need to pass arrayList with all venue instances to VenueFragment,
                *however venue variable is empty after it is passed
                *getItem() has to return Fragment and i don't know how to return fragment
                * within coroutine scope with passed argument

                CoroutineScope(coroutineContext).launch {
                    val user = UserPresenter.getInstance(context)
                    val venueList = user.showFavouriteVenues() as ArrayList<Venue>
                    Log.e("list is", venueList.toString())
                    val bundle = Bundle()
                    bundle.putParcelableArrayList("venue", venueList)

                    val fragInfo = VenuesFragment()
                    VenuesFragment().arguments = bundle
                }
                 */
                VenuesFragment()
            }
            1 -> {
                PerformersFragment()
            }
            else -> {
                EventsFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }
}
