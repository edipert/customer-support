package com.ediperturk.customer.ui.postlist

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ediperturk.customer.R
import com.ediperturk.customer.databinding.PostListFragmentBinding
import com.ediperturk.customer.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostListFragment : BaseFragment<PostListViewModel, PostListFragmentBinding>() {

    private val args by navArgs<PostListFragmentArgs>()

    override val viewModel: PostListViewModel by viewModels()

    override fun getLayoutId() = R.layout.post_list_fragment

    override fun initUserInterface() {
        viewModel.getPosts(args.userId)
    }
}