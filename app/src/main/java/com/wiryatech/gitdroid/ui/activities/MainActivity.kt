package com.wiryatech.gitdroid.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.wiryatech.gitdroid.ui.fragments.FavoritesFragment
import com.wiryatech.gitdroid.ui.fragments.HomeFragment
import com.wiryatech.gitdroid.R
import com.wiryatech.gitdroid.ui.fragments.SettingsFragment


class MainActivity : AppCompatActivity() {

    private lateinit var chipNavigationBar: ChipNavigationBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chipNavigationBar = findViewById(R.id.nav_bar)
        chipNavigationBar.setItemSelected(R.id.navigation_home, true)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()

        selectNavbarMenu()

//        val navView: ChipNavigationBar = findViewById(R.id.nav_bar)
//
//        val navController = findNavController(R.id.fragment_container)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.navigation_home, R.id.navigation_favorites, R.id.navigation_settings))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

    private fun selectNavbarMenu() {
        chipNavigationBar.setOnItemSelectedListener(object:
            ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(id: Int) {
                lateinit var fragment: Fragment
                when(id) {
                    R.id.navigation_home -> fragment = HomeFragment()
                    R.id.navigation_favorites -> fragment = FavoritesFragment()
                    R.id.navigation_settings -> fragment = SettingsFragment()
                }
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
            }
        })
    }

}