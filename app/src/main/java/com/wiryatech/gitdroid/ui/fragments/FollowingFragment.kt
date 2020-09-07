package com.wiryatech.gitdroid.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiryatech.gitdroid.R
import com.wiryatech.gitdroid.ui.activities.DetailActivity
import com.wiryatech.gitdroid.ui.adapters.UserAdapter
import com.wiryatech.gitdroid.ui.viewmodels.UserViewModel
import com.wiryatech.gitdroid.utils.Status
import kotlinx.android.synthetic.main.fragment_follower.*

class FollowingFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    private val userAdapter by lazy { UserAdapter() }

    companion object {
        const val USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as DetailActivity).userViewModel
        setupRV()
        handleState()

        Log.d("BundleFragment1", "$savedInstanceState, $arguments")
        if (arguments != null) {
            val username = arguments?.getString(FollowerFragment.USERNAME)
            getFollowing(username.toString())
            Log.d("BundleFragment1", "$arguments, $username")
        }
    }

    private fun setupRV() {
        rv_user.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun getFollowing(username: String) {
        if (username.trim().isNotEmpty()) {
            viewModel.getFollowing(username)
        }
    }

    private fun handleState() {
        viewModel.listFollowing.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Status.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        if (it.isNullOrEmpty()) {
                            showDefaultNoData()
                        } else {
                            userAdapter.differ.submitList(it)
                        }
                    }
                }
                is Status.Error -> {
                    hideProgressBar()
                    imageView.visibility = View.VISIBLE
                    tv_error.visibility = View.VISIBLE
                    response.message?.let {
                        tv_error.text = response.message
                        Log.e("Error", "${response.message}")
                    }
                }
                is Status.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        imageView.visibility = View.INVISIBLE
        tv_error.visibility = View.INVISIBLE
    }

    private fun showDefaultNoData() {
        imageView.visibility = View.VISIBLE
        tv_error.visibility = View.VISIBLE
        rv_user.visibility = View.INVISIBLE
        hideProgressBar()
    }

}