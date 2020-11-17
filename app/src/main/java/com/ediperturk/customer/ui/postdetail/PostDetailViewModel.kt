package com.ediperturk.customer.ui.postdetail

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ediperturk.customer.api.base.Result
import com.ediperturk.customer.api.data.entity.Post
import com.ediperturk.customer.api.data.entity.User
import com.ediperturk.customer.api.repository.user.UserRepository
import com.ediperturk.customer.common.alert
import com.ediperturk.customer.common.dismiss
import com.ediperturk.customer.common.progress
import com.ediperturk.customer.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PostDetailViewModel @ViewModelInject constructor(
    private val navController: NavController,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val commentListAdapter by lazy {
        CommentListAdapter()
    }

    val user = ObservableField<User>()
    val post = ObservableField<Post>()
    val commentCount = ObservableField<Int>()

    fun getComments(postId: Int) {
        viewModelScope.launch {
            userRepository.getComments(postId)
                .onStart {
                    navController.progress()
                }.collect { result ->
                    navController.dismiss()

                    when (result) {
                        is Result.Success -> result.data.let {
                            user.set(it.user)
                            post.set(it.post)
                            commentCount.set(it.comments.size)
                            commentListAdapter.submitList(it.comments)
                        }

                        is Result.Error -> result.exception.let {
                            navController.alert(
                                it.title,
                                it.message
                            )
                        }
                    }
                }
        }
    }
}