package com.ediperturk.customer.ui.postdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ediperturk.customer.R
import com.ediperturk.customer.api.data.entity.Comment
import com.ediperturk.customer.databinding.CommentItemBinding

class CommentListAdapter :
    ListAdapter<Comment, CommentListAdapter.CommentViewHolder>(CommentDataAdapterListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.comment_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class CommentDataAdapterListDiff : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }
    }

    inner class CommentViewHolder(
        private val viewBinding: CommentItemBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(comment: Comment) {
            viewBinding.comment = comment
            viewBinding.executePendingBindings()
        }
    }
}