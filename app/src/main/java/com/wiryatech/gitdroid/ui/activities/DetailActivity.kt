package com.wiryatech.gitdroid.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wiryatech.gitdroid.R
import com.wiryatech.gitdroid.data.db.UserDb
import com.wiryatech.gitdroid.data.model.User
import com.wiryatech.gitdroid.data.repositories.UserRepository
import com.wiryatech.gitdroid.ui.adapters.SectionsPagerAdapter
import com.wiryatech.gitdroid.ui.viewmodels.UserViewModel
import com.wiryatech.gitdroid.ui.viewmodels.UserViewModelFactory
import com.wiryatech.gitdroid.utils.Status
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var userViewModel: UserViewModel
    private val TAG = "DetailActivity"

    companion object {
        const val USERNAME = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val username = intent.getStringExtra(USERNAME).toString()

        initUI()
        initViewModel()
        initData(username)
        handleState()
        initPager(username)
    }

    private fun initUI() {
        btn_back.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun initViewModel() {
        val userRepository = UserRepository(UserDb(this))
        val viewModelFactory = UserViewModelFactory(userRepository)
        userViewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
    }

    private fun initPager(username: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.setData(username)
        vp_detail.adapter = sectionsPagerAdapter
        tab_detail.setupWithViewPager(vp_detail)
    }

    private fun initData(username: String) {
        if (username.trim().isNotEmpty()) {
            userViewModel.getDetailUser(username)
        }
    }

    private fun handleState() {
        Log.d(TAG, "handleState")
        userViewModel.detailUser.observe(this, { response ->
            when(response) {
                is Status.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        if (it.equals(null)) {
                            hideProgressBar()
                        } else {
                            inputData(it)
                        }
                    }
                }
                is Status.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Log.e("Error", "${response.message}")
                    }
                }
                is Status.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun inputData(it: User) {
        Glide.with(this)
            .load(it.avatar_url)
            .apply(RequestOptions().override(64).placeholder(R.drawable.ic_round_account_circle_64))
            .into(iv_avatar)

        tv_username.text = it.login
        tv_user_type.text = it.type
        tv_repos.text = it.public_repos.toString()
        tv_followers.text = it.followers.toString()
        tv_following.text = it.following.toString()
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

}