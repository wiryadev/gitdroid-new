package com.wiryatech.gitdroid.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wiryatech.gitdroid.R
import com.wiryatech.gitdroid.data.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(private val listUser: MutableList<User>) : RecyclerView.Adapter<UsersAdapter.ListViewHolder>() {

//    private val mData = ArrayList<User>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

//    fun setData(items: List<User>){
//        mData.apply {
//            clear()
//            addAll(items)
//        }
//        notifyDataSetChanged()
//    }

    fun setData(items: List<User>) {
        listUser.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .apply(RequestOptions().override(64, 64))
                    .placeholder(R.drawable.ic_baseline_account_circle_64)
                    .into(iv_avatar)

                tv_username.text = user.login
                tv_user_type.text = user.type

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}