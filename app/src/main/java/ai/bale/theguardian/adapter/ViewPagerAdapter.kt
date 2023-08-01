package ai.bale.theguardian.adapter

import ai.bale.theguardian.fragment.MainFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(private val fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    private val fragments = listOf<MainFragment>(MainFragment("news"),
        MainFragment("world"), MainFragment("science"),
        MainFragment("sport"), MainFragment("environment"),
        MainFragment("society"), MainFragment("fashion"),
        MainFragment("business"), MainFragment("culture"))

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments.get(index = position)
}