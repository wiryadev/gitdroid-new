package com.wiryatech.gitdroid.ui.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wiryatech.gitdroid.R
import com.wiryatech.gitdroid.ui.fragments.FollowerFragment
import com.wiryatech.gitdroid.ui.fragments.FollowingFragment

class SectionsPagerAdapter(private val mContext: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(
    fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var username = "test"

    companion object {
        const val USERNAME = "username"
    }

    private val tabTitles = intArrayOf(
        R.string.followers,
        R.string.following
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowerFragment()
                val bundle = Bundle()
                bundle.putString(USERNAME, getData())
                fragment.arguments = bundle
                Log.d("BundleFragmentVP", fragment.arguments.toString())
            }
            1 -> {
                fragment = FollowingFragment()
                val bundle = Bundle()
                bundle.putString(USERNAME, getData())
                fragment.arguments = bundle
                Log.d("BundleFragmentVP1", fragment.arguments.toString())
            }
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTitles[position])
    }

    override fun getCount(): Int {
        return 2
    }

    fun setData(user: String) {
        username = user
    }

    private fun getData(): String {
        return username
    }

}