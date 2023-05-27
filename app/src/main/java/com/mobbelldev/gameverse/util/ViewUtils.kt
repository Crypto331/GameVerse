package com.mobbelldev.gameverse.util

import android.view.View
import androidx.viewbinding.ViewBinding

object ViewUtils {
    fun setVisibility(binding: ViewBinding, vararg viewPairs: Pair<Int, Boolean>) {
        for (viewPair in viewPairs) {
            val view = binding.root.findViewById<View>(viewPair.first)
            view.visibility = if (viewPair.second) View.VISIBLE else View.GONE
        }
    }
}