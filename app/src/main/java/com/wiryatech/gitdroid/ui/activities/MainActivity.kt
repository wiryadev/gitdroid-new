package com.wiryatech.gitdroid.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.wiryatech.gitdroid.R
import com.wiryatech.gitdroid.data.db.UserDb
import com.wiryatech.gitdroid.data.repositories.UserRepository
import com.wiryatech.gitdroid.ui.fragments.FavoritesFragment
import com.wiryatech.gitdroid.ui.fragments.HomeFragment
import com.wiryatech.gitdroid.ui.fragments.SettingsFragment
import com.wiryatech.gitdroid.ui.viewmodels.UserViewModel
import com.wiryatech.gitdroid.ui.viewmodels.UserViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var chipNavigationBar: ChipNavigationBar
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()

        initUI()
        selectNavigationMenu()
    }

    private fun initViewModel() {
        val userRepository = UserRepository(UserDb(this))
        val viewModelFactory = UserViewModelFactory(userRepository)
        userViewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
    }

    private fun initUI() {
        chipNavigationBar = findViewById(R.id.nav_bar)
        chipNavigationBar.setItemSelected(R.id.navigation_home, true)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
    }

    private fun selectNavigationMenu() {
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