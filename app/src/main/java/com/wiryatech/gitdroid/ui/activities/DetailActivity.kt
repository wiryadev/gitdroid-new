package com.wiryatech.gitdroid.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.wiryatech.gitdroid.R
import com.wiryatech.gitdroid.data.db.UserDb
import com.wiryatech.gitdroid.data.repositories.UserRepository
import com.wiryatech.gitdroid.ui.adapters.SectionsPagerAdapter
import com.wiryatech.gitdroid.ui.fragments.FollowerFragment
import com.wiryatech.gitdroid.ui.fragments.FollowingFragment
import com.wiryatech.gitdroid.ui.viewmodels.UserViewModel
import com.wiryatech.gitdroid.ui.viewmodels.UserViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var userViewModel: UserViewModel

    companion object {
        const val USERNAME = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val username = intent.getStringExtra(USERNAME).toString()
        tv_username.text = username

        initViewModel()

        initPager(username)
    }

    private fun initPager(username: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.setData(username)
        vp_detail.adapter = sectionsPagerAdapter
        tab_detail.setupWithViewPager(vp_detail)
    }

    private fun initViewModel() {
        val userRepository = UserRepository(UserDb(this))
        val viewModelFactory = UserViewModelFactory(userRepository)
        userViewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
    }
}