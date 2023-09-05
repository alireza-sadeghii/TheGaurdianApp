package ai.bale.theguardian

import ai.bale.theguardian.adapter.ViewPagerAdapter
import ai.bale.theguardian.data.SettingsData
import ai.bale.theguardian.databinding.ActivityMainBinding
import ai.bale.theguardian.databinding.ToolbarMainBinding
import ai.bale.theguardian.settings.SettingsActivity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var mainBinding: ActivityMainBinding
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val toolbarBinding = ToolbarMainBinding.inflate(layoutInflater)

        setContentView(mainBinding.root)


        val toolbar = mainBinding.toolbar
        setSupportActionBar(toolbar)


        viewPager = mainBinding.viewpager
        val viewAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = viewAdapter
        viewPager.offscreenPageLimit = 10


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
        val toggleButton = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_menu,
            R.string.close_menu
        )
        drawerLayout.addDrawerListener(toggleButton)
        toggleButton.syncState()


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
        })


        if (savedInstanceState != null) {
            val selectedTabPosition = savedInstanceState.getInt("selectedTabPosition", 0)
            viewPager.currentItem = selectedTabPosition

            val settingsBundle = savedInstanceState.getBundle("settingsBundle")
            if (settingsBundle != null) {
                SettingsData.numOfItems = settingsBundle.getString("numOfItems", "10") ?: "10"
                SettingsData.orderBy = settingsBundle.getString("orderBy", "oldest") ?: "oldest"
                SettingsData.orderDate = settingsBundle.getString("orderDate", "published") ?: "published"
                SettingsData.colorTheme = settingsBundle.getString("colorTheme", "#FFFFFF") ?: "#FFFFFF"
                SettingsData.textScale = settingsBundle.getString("textScale", "1") ?: "1"
            }
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val viewPager = mainBinding.viewpager
        val drawerLayout = mainBinding.drawerLayoutMain

        when (item.itemId) {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_option_menu -> {
                val settingIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingIntent)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        val preference = PreferenceManager.getDefaultSharedPreferences(baseContext)
        SettingsData.numOfItems = preference.getString("numOfItem","10") ?: "10"
        SettingsData.orderBy = preference.getString("orderBy", "oldest") ?: "oldest"
        SettingsData.orderDate = preference.getString("orderDate", "published") ?: "published"
        SettingsData.colorTheme = preference.getString("colorTheme", "#FFFFFF") ?: "#FFFFFF"
        SettingsData.textScale = preference.getString("textScale", "1") ?: "1"
    }

    /*private fun setFontSize(context: Context): Context{
        val configuration = context.resources.configuration
        configuration.fontScale = SettingsData.textScale.toFloat()
        return context.createConfigurationContext(configuration)
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase == null) return
        val configuration = setFontSize(newBase)
        super.attachBaseContext(configuration)
    }*/

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentItem", viewPager.currentItem)
        outState.putInt("selectedTabPosition", viewPager.currentItem)

        val settingsBundle = Bundle()
        settingsBundle.putString("numOfItems", SettingsData.numOfItems)
        settingsBundle.putString("orderBy", SettingsData.orderBy)
        settingsBundle.putString("orderDate", SettingsData.orderDate)
        settingsBundle.putString("colorTheme", SettingsData.colorTheme)
        settingsBundle.putString("textScale", SettingsData.textScale)
        outState.putBundle("settingsBundle", settingsBundle)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val currentItem = savedInstanceState.getInt("currentItem", 0)
        viewPager.currentItem = currentItem
        val selectedTabPosition = savedInstanceState.getInt("selectedTabPosition", 0)
        mainBinding.toolbarTabs.getTabAt(selectedTabPosition)?.select()
    }


}