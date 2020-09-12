package com.wiryatech.gitdroid.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiryatech.gitdroid.R
import com.wiryatech.gitdroid.ui.activities.DetailActivity
import com.wiryatech.gitdroid.ui.activities.MainActivity
import com.wiryatech.gitdroid.ui.adapters.UserAdapter
import com.wiryatech.gitdroid.ui.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    private val userAdapter by lazy { UserAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).userViewModel

        setupRV()
        observe()
        initUI()
    }

    private fun setupRV() {
        rv_fav.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun observe() {
        viewModel.getFavoriteUsers().observe(viewLifecycleOwner, {
            userAdapter.differ.submitList(it)
        })

        viewModel.getAmountOfData().observe(viewLifecycleOwner, {
            tv_fav.text = it.toString()

            if (it <= 0) {
                imageView.visibility = View.VISIBLE
                tv_error.visibility = View.VISIBLE
            }
        })
    }

    private fun initUI() {
        userAdapter.setOnItemClickListener {
            val data = it.login
            getUserDetail(data)
        }
    }

    private fun getUserDetail(data: String) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.USERNAME, data)
        startActivity(intent)
    }

}