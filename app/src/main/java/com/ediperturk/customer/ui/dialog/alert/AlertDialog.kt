package com.ediperturk.customer.ui.dialog.alert

import androidx.navigation.fragment.navArgs
import com.ediperturk.customer.R
import com.ediperturk.customer.ui.base.BaseDialogFragment
import kotlinx.android.synthetic.main.alert_dialog.*

class AlertDialog : BaseDialogFragment() {

    companion object {
        const val TAG = "com.ediperturk.customer.ui.dialog.alert.AlertDialog"
    }

    override val args: AlertDialogArgs by navArgs()

    override fun getDialogTag() = TAG

    override fun getLayoutId() = R.layout.alert_dialog

    override fun initUserInterface() {
        alertDialogTitle.text = args.title
        alertDialogMessage.text = args.message

        alertDialogOkButton.setOnClickListener {
            close()
        }
    }
}