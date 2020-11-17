package com.ediperturk.customer.ui.userdetail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ediperturk.customer.R
import com.ediperturk.customer.databinding.UserDetailFragmentBinding
import com.ediperturk.customer.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : BaseFragment<UserDetailViewModel, UserDetailFragmentBinding>() {

    val args by navArgs<UserDetailFragmentArgs>()

    override val viewModel: UserDetailViewModel by viewModels()

    override fun getLayoutId() = R.layout.user_detail_fragment

    override fun initUserInterface() {
        viewModel.getUser(args.userId)
    }
}