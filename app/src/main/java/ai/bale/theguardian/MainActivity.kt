package ai.bale.theguardian

import ai.bale.theguardian.adapter.ViewPagerAdapter
import ai.bale.theguardian.databinding.ActivityMainBinding
import ai.bale.theguardian.databinding.ToolbarMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.get
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    lateinit var mainBinding: ActivityMainBinding
    lateinit var toolbarBinding: ToolbarMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        toolbarBinding = ToolbarMainBinding.inflate(layoutInflater)

        setContentView(mainBinding.root)


        val toolbar = mainBinding.toolbar
        setSupportActionBar(toolbar)



        val viewPager = mainBinding.viewpager
        val viewAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = viewAdapter
        viewPager.offscreenPageLimit = 2



        val navigationView = mainBinding.navigationViewMain
        navigationView.setNavigationItemSelectedListener(this)

        val tabLayout = mainBinding.toolbarTabs
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
                navigationView.menu[tab.position].isChecked = true
                tab.select()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))



        val drawerLayout = mainBinding.drawerLayoutMain
        val toggleButton = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_menu, R.string.close_menu)
        drawerLayout.addDrawerListener(toggleButton)
        toggleButton.syncState()

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val drawerLayout = mainBinding.drawerLayoutMain
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val viewPager = mainBinding.viewpager
        val drawerLayout = mainBinding.drawerLayoutMain

        when(item.itemId){
            R.id.menu_home -> {
                viewPager.currentItem = 0
            }
            R.id.menu_world -> {
                viewPager.currentItem = 1
            }
            R.id.menu_science -> {
                viewPager.currentItem = 2
            }
            R.id.menu_sport -> {
                viewPager.currentItem = 3
            }
            R.id.menu_environment -> {
                viewPager.currentItem = 4
            }
            R.id.menu_society -> {
                viewPager.currentItem = 5
            }
            R.id.menu_fashion -> {
                viewPager.currentItem = 6
            }
            R.id.menu_business -> {
                viewPager.currentItem = 7
            }
            R.id.menu_culture -> {
                viewPager.currentItem = 8
            }
        }

        item.isChecked = true
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}