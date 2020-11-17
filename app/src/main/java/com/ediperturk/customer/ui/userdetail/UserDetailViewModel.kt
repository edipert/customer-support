package com.ediperturk.customer.ui.userdetail

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ediperturk.customer.api.base.Result
import com.ediperturk.customer.api.data.entity.User
import com.ediperturk.customer.api.repository.user.UserRepository
import com.ediperturk.customer.common.alert
import com.ediperturk.customer.common.dismiss
import com.ediperturk.customer.common.progress
import com.ediperturk.customer.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class UserDetailViewModel @ViewModelInject constructor(
    private val navController: NavController,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val user = ObservableField<User>()

    fun getUser(userId: Int) {
        viewModelScope.launch {
            userRepository.getUser(userId)
                .onStart {
                    navController.progress()
                }.collect { result ->
                    navController.dismiss()

                    when (result) {
                        is Result.Success -> result.data.let {
                            user.set(it)
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