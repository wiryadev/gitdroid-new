package com.wiryatech.gitdroid.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wiryatech.gitdroid.R
import com.wiryatech.gitdroid.data.db.UserDb
import com.wiryatech.gitdroid.data.model.User
import com.wiryatech.gitdroid.data.repositories.DetailRepository
import com.wiryatech.gitdroid.ui.adapters.SectionsPagerAdapter
import com.wiryatech.gitdroid.ui.viewmodels.DetailViewModel
import com.wiryatech.gitdroid.ui.viewmodels.DetailViewModelFactory
import com.wiryatech.gitdroid.ui.viewmodels.UserViewModel
import com.wiryatech.gitdroid.utils.Resource
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var detailViewModel: DetailViewModel
    lateinit var username: String
    lateinit var user: User
    private val TAG = "DetailActivity"
    private var isFavorite = false

    companion object {
        const val USERNAME = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        username = intent.getStringExtra(USERNAME).toString()

        initUI()
        initViewModel()
        initData(username)
        handleState()
        checkFavorite()
        initPager(username)
    }

    private fun initUI() {
        initBackButton()
    }

    private fun initBackButton() {
        btn_back.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun initViewModel() {
        val detailRepository = DetailRepository((UserDb(this)))
        val viewModelFactory = DetailViewModelFactory(detailRepository)
        detailViewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
    }

    private fun initPager(username: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.setData(username)
        vp_detail.adapter = sectionsPagerAdapter
        tab_detail.setupWithViewPager(vp_detail)
    }

    private fun initData(username: String) {
        if (username.trim().isNotEmpty()) {
            detailViewModel.getDetailUser(username)
        }
    }

    private fun checkFavorite() {
        detailViewModel.checkFavorite(username).observe(this, {
            if (it == 1) {
                isFavorite = true
                changeIcon(isFavorite)
            } else {
                isFavorite = false
                changeIcon(isFavorite)
            }
        })
    }

    private fun handleState() {
        Log.d(TAG, "handleState")
        detailViewModel.detailUser.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        if (it.equals(null)) {
                            hideProgressBar()
                        } else {
                            user = it
                            inputData(user)
                            saveUser(user)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Log.e("Error", "${response.message}")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun saveUser(user: User) {
        btn_fav.setOnClickListener {
            checkFavorite()

            if (isFavorite) {
                detailViewModel.deleteUser(user)
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
            } else {
                detailViewModel.insertFavoriteUser(user)
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            }
        }
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

    private fun changeIcon(saved: Boolean) {
        if (saved) {
            btn_fav.setImageResource(R.drawable.ic_round_favorite_24)
        } else {
            btn_fav.setImageResource(R.drawable.ic_round_favorite_border_24)
        }
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

}