package br.com.popularmovies.utils

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("isVisible")
    fun showView(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}