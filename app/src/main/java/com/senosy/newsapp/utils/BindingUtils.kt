package com.senosy.newsapp.utils

import android.view.View
import androidx.databinding.BindingAdapter


object BindingUtils {

    @JvmStatic
    @BindingAdapter("visibleIf")
    fun changeVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("invisibleIf")
    fun changeInvisibility(view: View, inVisible: Boolean) {
        view.visibility = if (inVisible) View.INVISIBLE else View.VISIBLE
    }

//    @JvmStatic
//    @BindingAdapter("isStartShimmer")
//    fun onshimmerLoading(
//        shimmerLayout: ShimmerFrameLayout,
//        start: Boolean
//    ) {
//        if (start) shimmerLayout.startShimmerAnimation() else shimmerLayout.stopShimmerAnimation()
//    }

}