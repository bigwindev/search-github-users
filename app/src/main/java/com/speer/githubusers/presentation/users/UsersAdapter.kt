package com.speer.githubusers.presentation.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.speer.githubusers.R
import com.speer.githubusers.databinding.ItemLoadingBinding
import com.speer.githubusers.databinding.ItemUserBinding
import com.speer.githubusers.domain.model.User

class UsersAdapter(
    private val itemListener: OnClickListener,
    private val followersListener: OnClickListener,
    private val followingListener: OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var users: Array<User> = arrayOf()
    private var hasLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.ITEM.value -> ItemViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_user,
                    parent,
                    false
                )
            )
            else -> LoadingViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_loading,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind(
                this.users[position],
                this.itemListener,
                this.followersListener,
                this.followingListener
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return (if (position < this.users.size) ViewType.ITEM else ViewType.LOADING).value
    }

    override fun getItemCount(): Int {
        return if (this.hasLoading) this.users.size + 1 else this.users.size
    }

    fun setUsers(users: Array<User>) {
        this.users = users
        this.hasLoading = false
        notifyDataSetChanged()
    }

    fun hasLoading(): Boolean {
        return this.hasLoading
    }

    fun addLoading() {
        this.hasLoading = true
        notifyItemInserted(this.users.size)
    }

    private enum class ViewType(val value: Int) {
        ITEM(0), LOADING(1)
    }

    private class ItemViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            user: User,
            itemListener: OnClickListener,
            followersListener: OnClickListener,
            followingListener: OnClickListener
        ) {
            this.binding.user = user
            this.binding.itemListener = itemListener
            this.binding.followersListener = followersListener
            this.binding.followingListener = followingListener
            this.binding.executePendingBindings()
        }
    }

    private class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)
}

class OnClickListener(val clickListener: (user: User) -> Unit) {
    fun onClick(user: User) = clickListener(user)
}
