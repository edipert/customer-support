package com.ediperturk.customer.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ediperturk.customer.R
import com.ediperturk.customer.api.data.entity.User
import com.ediperturk.customer.databinding.UserItemBinding

class UserListAdapter(
    private val onClick: (User, Action) -> Unit,
) : ListAdapter<User, UserListAdapter.UserViewHolder>(UserDataAdapterListDiff()) {

    enum class Action {
        POST, DETAIL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.user_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class UserDataAdapterListDiff : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    inner class UserViewHolder(
        private val viewBinding: UserItemBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(user: User) {
            viewBinding.user = user
            viewBinding.executePendingBindings()

            viewBinding.root.setOnClickListener {
                onClick(user, Action.POST)
            }

            viewBinding.initialsTextView.setOnClickListener {
                onClick(user, Action.DETAIL)
            }
        }
    }
}