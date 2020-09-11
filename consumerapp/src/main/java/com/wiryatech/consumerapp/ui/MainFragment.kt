package com.wiryatech.consumerapp.ui

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiryatech.consumerapp.R
import com.wiryatech.consumerapp.adapter.UserAdapter
import com.wiryatech.consumerapp.models.User
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var userAdapter: UserAdapter

    companion object {
        private const val AUTHORITY = "com.wiryatech.gitdroid"
        private const val TABLE_NAME = "user_db"

        private const val LOGIN = "login"
        private const val TYPE = "type"
        private const val AVATAR = "avatar_url"
        private const val REPOS = "public_repos"
        private const val FOLLOWERS = "followers"
        private const val FOLLOWING = "following"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataList()
    }

    private fun getDataList() {
        val uriBuilder = Uri.Builder().apply {
            scheme("content")
            authority(AUTHORITY)
            appendPath(TABLE_NAME)
        }
        val uri = uriBuilder.build()

        val contentResolver = requireContext().contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)

        if (cursor != null) {
            if (cursor.count <= 0) {
                setNoDataState()
            } else {
                setupRV(cursor)
                initUI()
            }
        }
    }

    private fun initUI() {
        userAdapter.setOnItemClickListener {
            val followers = it.followers
            val following = it.following
            Toasty.custom(
                requireContext(),
                "Followers $followers   Following: $following",
                R.drawable.ic_round_account_circle_64,
                R.color.colorAccentSecondary,
                Toast.LENGTH_SHORT,
                true,
                true)
                .show()
        }
    }

    private fun getUserList(cursor: Cursor): List<User> {
        val userList: MutableList<User> = mutableListOf()

        cursor.apply {
            while (moveToNext()) {
                userList.add(
                    User(
                        getString(getColumnIndexOrThrow(LOGIN)),
                        getString(getColumnIndexOrThrow(TYPE)),
                        getString(getColumnIndexOrThrow(AVATAR)),
                        getInt(getColumnIndexOrThrow(REPOS)),
                        getInt(getColumnIndexOrThrow(FOLLOWERS)),
                        getInt(getColumnIndexOrThrow(FOLLOWING))
                    )
                )
            }
            close()
        }

        return userList.toList()
    }

    private fun setupRV(cursor: Cursor) {
        rv_user.apply {
            userAdapter = UserAdapter(getUserList(cursor))
            adapter = userAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setNoDataState() {
        imageView.visibility = View.VISIBLE
        tv_error.visibility = View.VISIBLE
    }

}