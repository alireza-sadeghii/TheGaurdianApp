package ai.bale.theguardian

import ai.bale.theguardian.adapter.ViewPagerAdapter
import ai.bale.theguardian.data.SettingsViewModel
import ai.bale.theguardian.databinding.ActivityMainBinding
import ai.bale.theguardian.databinding.ToolbarMainBinding
import ai.bale.theguardian.settings.SettingsActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    lateinit var mainBinding: ActivityMainBinding
    lateinit var viewPager: ViewPager
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        applySettings()
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val toolbarBinding = ToolbarMainBinding.inflate(layoutInflater)

        setContentView(mainBinding.root)


        val toolbar = mainBinding.toolbar
        setSupportActionBar(toolbar)



        viewPager = mainBinding.viewpager
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


        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
        })


        if (savedInstanceState != null) {
            val selectedTabPosition = savedInstanceState.getInt("selectedTabPosition", 0)
            viewPager.currentItem = selectedTabPosition
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedTabPosition", viewPager.currentItem)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_option_menu -> {
                val settingIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingIntent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun applySettings(){
        try{
            Log.v("checking",viewModel.getNumberOfItems().toString())
        }catch (e : Exception){
            Log.v("checking",e.message.toString())
        }
    }

}