package com.ediperturk.customer.ui.main

import androidx.fragment.app.viewModels
import com.ediperturk.customer.R
import com.ediperturk.customer.databinding.MainFragmentBinding
import com.ediperturk.customer.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel, MainFragmentBinding>() {

    override val viewModel: MainViewModel by viewModels()

    override fun getLayoutId() = R.layout.main_fragment

    override fun initUserInterface() {

    }
}