package com.wiryatech.gitdroid.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiryatech.gitdroid.R
import com.wiryatech.gitdroid.data.model.User
import com.wiryatech.gitdroid.ui.adapters.UsersAdapter
import com.wiryatech.gitdroid.ui.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private val usersAdapter by lazy { UsersAdapter(mutableListOf()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showDefaultNoData()

        initUI()
        setupRV()
    }

//    private fun initUI() {
//        search.apply{
//            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    if (query.toString().isEmpty()) return false
//                    searchViewModel.searchUsers(query!!)
//                    searchViewModel.listUsers.observe(viewLifecycleOwner, { response ->
//                        if (response.isSuccessful) {
//                            response.body()?.let { usersAdapter.setData(it) }
//                            showRV()
//                        } else {
//                            Toast.makeText(activity, response.code(), Toast.LENGTH_SHORT).show()
//                        }
//                    })
//                    return true
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    return false
//                }
//            })
//        }
//    }

    private fun initUI() {
        search.apply{
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchUser(query.toString())
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun searchUser(q: String) {
        if (q.trim().isNotEmpty()) {
            searchViewModel.searchUsers(q)
        }
    }

    private fun setupRV() {
        usersAdapter.notifyDataSetChanged()

        rv_user.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = usersAdapter
        }

        searchViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(SearchViewModel::class.java)
        searchViewModel.status.observe(viewLifecycleOwner, { setLoadingState(it) })
        searchViewModel.listSearch.observe(viewLifecycleOwner, {showRV(it)})

        usersAdapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun showRV(users: List<User>) {
        if (!users.isNullOrEmpty()) {
            rv_user.adapter?.let {
                if (it is UsersAdapter) {
                    it.setData(users)
                }
            }
        } else {
            showDefaultNoData()
        }
    }

    private fun showSelectedUser(user: User) {
        Toast.makeText(activity, user.login, Toast.LENGTH_SHORT).show()
    }

    private fun setLoadingState(state: Boolean) {
        if (state) {
            imageView.visibility = View.INVISIBLE
            tv_error.visibility = View.INVISIBLE
            rv_user.visibility = View.INVISIBLE
            rv_user_loading.visibility = View.VISIBLE
        } else {
            Handler().postDelayed({
                rv_user_loading.stopShimmer()
                rv_user_loading.visibility = View.INVISIBLE
                rv_user.visibility = View.VISIBLE
            }, 1250)
        }
    }

    private fun showDefaultNoData() {
        imageView.visibility = View.VISIBLE
        tv_error.visibility = View.VISIBLE
        rv_user.visibility = View.INVISIBLE
        rv_user_loading.visibility = View.INVISIBLE
    }

}