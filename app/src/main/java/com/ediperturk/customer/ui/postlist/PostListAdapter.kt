package com.ediperturk.customer.ui.postlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ediperturk.customer.R
import com.ediperturk.customer.api.data.entity.Post
import com.ediperturk.customer.api.data.model.PostComment
import com.ediperturk.customer.databinding.BasicCommentItemBinding
import com.ediperturk.customer.databinding.PostItemBinding

class PostListAdapter(
    private val onClick: (Post) -> Unit,
    private val getComments: (Int, (PostComment) -> Unit) -> Unit
) : ListAdapter<Post, PostListAdapter.PostViewHolder>(PostDataAdapterListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.post_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class PostDataAdapterListDiff : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    inner class PostViewHolder(
        private val viewBinding: PostItemBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(post: Post) {
            viewBinding.post = post
            viewBinding.commentCount = 0
            viewBinding.executePendingBindings()

            viewBinding.root.setOnClickListener {
                onClick(post)
            }

            viewBinding.seeCommentsTextView.setOnClickListener {
                if (viewBinding.commentsLayout.childCount == 0) {
                    getComments(post.id) { postComment ->
                        viewBinding.commentCount = postComment.comments.size
                        viewBinding.seeCommentsTextView.setText(R.string.text_hide_comments)

                        postComment.comments.forEach {
                            addComment(it.body)
                        }

                        addComment(itemView.context.getString(R.string.text_see_more))
                    }
                } else {
                    viewBinding.commentCount = 0
                    viewBinding.seeCommentsTextView.setText(R.string.text_see_comments)
                    viewBinding.commentsLayout.removeAllViews()
                }
            }
        }

        private fun addComment(comment: String) {
            val binding = DataBindingUtil.inflate<BasicCommentItemBinding>(
                LayoutInflater.from(itemView.context),
                R.layout.basic_comment_item,
                viewBinding.commentsLayout,
                false
            )

            binding.text = comment
            binding.executePendingBindings()
            viewBinding.commentsLayout.addView(binding.root)
        }
    }
}