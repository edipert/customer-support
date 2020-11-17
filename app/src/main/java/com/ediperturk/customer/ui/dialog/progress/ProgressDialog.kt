package com.ediperturk.customer.ui.dialog.progress

import androidx.navigation.fragment.navArgs
import com.ediperturk.customer.R
import com.ediperturk.customer.ui.base.BaseDialogFragment

class ProgressDialog : BaseDialogFragment() {
    companion object {
        const val TAG = "com.ediperturk.customer.ui.dialog.progress.ProgressDialog"
    }

    override val args: ProgressDialogArgs by navArgs()

    override fun getDialogTag() = TAG

    override fun getLayoutId() = R.layout.progress_dialog

    override fun initUserInterface() {
        //NOOP
    }
}