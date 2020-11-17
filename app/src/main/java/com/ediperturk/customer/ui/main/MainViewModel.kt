package com.ediperturk.customer.ui.main

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ediperturk.customer.api.base.Result
import com.ediperturk.customer.api.repository.user.UserRepository
import com.ediperturk.customer.common.alert
import com.ediperturk.customer.common.dismiss
import com.ediperturk.customer.common.progress
import com.ediperturk.customer.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val navController: NavController?,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val isRefreshing = ObservableField(false)

    val userListAdapter by lazy {
        UserListAdapter { user, action ->
            navController?.navigate(
                when (action) {
                    UserListAdapter.Action.POST -> MainFragmentDirections.toPostListFragment(
                        user.id
                    )
                    UserListAdapter.Action.DETAIL -> MainFragmentDirections.toUserDetailFragment(
                        user.name,
                        user.id
                    )
                }
            )
        }
    }

    override fun onStart() {
        getUsers()
    }

    fun getUsers() {
        isRefreshing.set(true)

        viewModelScope.launch {
            userRepository.getUsers()
                .onStart {
                    isRefreshing.set(false)
                    navController?.progress()
                }.collect { result ->
                    navController?.dismiss()

                    when (result) {
                        is Result.Success -> {
                            userListAdapter.submitList(result.data)
                        }
                        is Result.Error -> result.exception.let {
                            navController?.alert(
                                it.title,
                                it.message
                            )
                        }
                    }
                }
        }
    }
}