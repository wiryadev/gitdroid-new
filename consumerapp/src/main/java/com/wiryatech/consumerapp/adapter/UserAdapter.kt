package com.wiryatech.consumerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wiryatech.consumerapp.R
import com.wiryatech.consumerapp.models.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user =  userList[position]
        holder.itemView.apply {
            Glide.with(this)
                .load(user.avatar_url)
                .apply(RequestOptions().override(64).placeholder(R.drawable.ic_round_account_circle_64))
                .into(iv_avatar)

            tv_username.text = user.login
            tv_user_type.text = user.type
            tv_public_repos.text = user.public_repos.toString()

            setOnClickListener {
                onItemClickListener?.let { it(user) }
            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    private var onItemClickListener: ((User) -> Unit)? = null

    fun setOnItemClickListener(listener: (User) -> Unit) {
        onItemClickListener = listener
    }

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}