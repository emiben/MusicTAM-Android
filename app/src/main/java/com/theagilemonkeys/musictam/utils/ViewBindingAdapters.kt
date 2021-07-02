package com.theagilemonkeys.musictam.utils

import android.view.View
import androidx.databinding.BindingAdapter

object ViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("isVisible")
    fun isVisible(view: View?, isVisible: Boolean?) {
        if (isVisible == true) {
            view?.visibility = View.VISIBLE
        } else {
            view?.visibility = View.GONE
        }
    }
}