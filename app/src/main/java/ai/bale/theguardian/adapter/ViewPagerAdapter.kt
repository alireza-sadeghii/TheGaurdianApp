package ai.bale.theguardian.adapter

import ai.bale.theguardian.fragment.MainFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int = 9

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> MainFragment("news")
            1 -> MainFragment("world")
            2 -> MainFragment("science")
            3 -> MainFragment("sport")
            4 -> MainFragment("environment")
            5 -> MainFragment("society")
            6 -> MainFragment("fashion")
            7 -> MainFragment("business")
            8 -> MainFragment("culture")
            else -> MainFragment("news")
        }
    }

}