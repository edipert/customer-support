package com.ediperturk.customer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ediperturk.customer.common.findNavController
import com.ediperturk.customer.navigation.annotation.NavigationHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*

@AndroidEntryPoint
@NavigationHost(R.id.nav_host_fragment)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Observe destination change
        findNavController().addOnDestinationChangedListener { _, destination, arguments ->
            backButton.visibility =
                if (destination.id != R.id.mainFragment && destination.navigatorName == "screen") View.VISIBLE
                else View.GONE

            // Set app bar title if there is a title variable in arguments
            val title = arguments?.getString("title")

            if (destination.navigatorName == "screen") {
                if (!title.isNullOrEmpty()) {
                    titleTextView.text = title
                } else {
                    titleTextView.text = destination.label
                }
            }
        }

        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        // Check is first fragment in fragment manager
        // iIf it first finish activity
        if (!findNavController().popBackStack()) {
            super.onBackPressed()
        }
    }
}