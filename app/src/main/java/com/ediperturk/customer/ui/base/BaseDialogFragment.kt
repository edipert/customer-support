package com.ediperturk.customer.ui.base

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavArgs
import com.ediperturk.customer.R
import com.ediperturk.customer.navigation.NavigationHostFragment

abstract class BaseDialogFragment : DialogFragment() {
    abstract val args: NavArgs

    protected var cancellable: Boolean = false
    protected var outsideTouch: Boolean = false

    abstract fun getDialogTag(): String

    protected abstract fun getLayoutId(): Int

    protected abstract fun initUserInterface()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStyle()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.window?.let {
            onDialogWindow(it)
        }

        isCancelable = cancellable
        dialog.setCanceledOnTouchOutside(outsideTouch)

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUserInterface()
    }

    open fun onDialogWindow(window: Window) {
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window.attributes.dimAmount = 0.9f
        }
    }

    open fun initStyle() = setStyle(STYLE_NO_FRAME, R.style.Window_Dialog)

    open fun close() {
        val parent = parentFragment
        if (parent is NavigationHostFragment) {
            parent.navController.popBackStack()
        }
    }
}