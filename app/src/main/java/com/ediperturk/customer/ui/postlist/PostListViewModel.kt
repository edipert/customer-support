package com.ediperturk.customer.ui.postlist

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ediperturk.customer.api.base.Result
import com.ediperturk.customer.api.data.entity.User
import com.ediperturk.customer.api.data.model.PostComment
import com.ediperturk.customer.api.repository.user.UserRepository
import com.ediperturk.customer.common.alert
import com.ediperturk.customer.common.dismiss
import com.ediperturk.customer.common.progress
import com.ediperturk.customer.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PostListViewModel @ViewModelInject constructor(
    private val navController: NavController,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val user = ObservableField<User>()

    val postListAdapter by lazy {
        PostListAdapter({
            navController.navigate(
                PostListFragmentDirections.toPostDetailFragment(
                    it.id
                )
            )
        }, this::getComments)
    }

    fun getPosts(userId: Int) {
        viewModelScope.launch {
            userRepository.getPosts(userId)
                .onStart {
                    navController.progress()
                }.collect { result ->
                    navController.dismiss()

                    when (result) {
                        is Result.Success -> result.data.let {
                            user.set(it.user)
                            postListAdapter.submitList(it.posts)
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

    private fun getComments(postId: Int, callback: (PostComment) -> Unit) {
        viewModelScope.launch {
            userRepository.getComments(postId)
                .collect { result ->
                    when (result) {
                        is Result.Success -> callback(result.data)
                        is Result.Error -> { }
                    }
                }
        }
    }
}