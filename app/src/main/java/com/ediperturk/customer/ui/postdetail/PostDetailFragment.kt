package com.ediperturk.customer.ui.postdetail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ediperturk.customer.R
import com.ediperturk.customer.databinding.PostDetailFragmentBinding
import com.ediperturk.customer.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailFragment : BaseFragment<PostDetailViewModel, PostDetailFragmentBinding>() {

    private val args by navArgs<PostDetailFragmentArgs>()

    override val viewModel: PostDetailViewModel by viewModels()

    override fun getLayoutId() = R.layout.post_detail_fragment

    override fun initUserInterface() {
        viewModel.getComments(args.postId)
    }
}