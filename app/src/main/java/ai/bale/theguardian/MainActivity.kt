package ai.bale.theguardian

import ai.bale.theguardian.adapter.ViewPagerAdapter
import ai.bale.theguardian.databinding.ActivityMainBinding
import ai.bale.theguardian.databinding.MainContentBinding
import ai.bale.theguardian.databinding.ToolbarMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout

private lateinit var mainBinding: ActivityMainBinding
private lateinit var barBinding: ToolbarMainBinding
private lateinit var contentBinding: MainContentBinding
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        barBinding = ToolbarMainBinding.inflate(layoutInflater)
        contentBinding = MainContentBinding.inflate(layoutInflater)

        val view = mainBinding.root
        setContentView(view)


        val viewPager = contentBinding.viewpager
        val tabLayout = barBinding.toolbarTabs


        val tabsTitle = listOf<String>("Home", "World", "Science", "Sport", "Environment",
            "Society", "Fashion", "Business", "Culture")

        for(title in tabsTitle){
            tabLayout.addTab(tabLayout.newTab().setText(title))
        }



        val toolbar = barBinding.toolbar
        setSupportActionBar(toolbar)

        val drawerLayout = mainBinding.drawerLayoutMain
        val toggleButton : ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_menu, R.string.close_menu)
        drawerLayout.addDrawerListener(toggleButton)
        toggleButton.syncState()

        val navigationView = mainBinding.navigationViewMain
        navigationView.setNavigationItemSelectedListener(this)


        val viewAdapter = ViewPagerAdapter(supportFragmentManager)
        println("pass")





        viewPager.adapter = viewAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }

    override fun onBackPressed() {
        val drawerLayout = mainBinding.drawerLayoutMain
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else{
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val viewPager = contentBinding.viewpager
        val drawerLayout = mainBinding.drawerLayoutMain

        when(item.itemId){
            R.id.menu_home -> {
                viewPager.currentItem = 0
                supportActionBar!!.title = R.string.home.toString()
            }

            //TODO: .....
        }

        item.isChecked = true
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}