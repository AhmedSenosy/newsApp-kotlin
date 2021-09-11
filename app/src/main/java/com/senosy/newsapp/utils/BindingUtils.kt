package com.senosy.newsapp.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


object BindingUtils {
    const val emptyImage = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.generationsforpeace.org%2Far%2Fstaff%2F%25D8%25B9%25D8%25A8%25D8%25AF%25D8%25A7%25D9%2584%25D9%2584%25D9%2587-%25D8%25A7%25D9%2584%25D8%25A8%25D9%2586%25D9%2588%25D9%258A%2Fempty-2%2F&psig=AOvVaw1nWXNZiOJxMAun3TJ6zaYU&ust=1631477277460000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCJC_is7c9_ICFQAAAAAdAAAAABAD";
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

    @JvmStatic
    @BindingAdapter("image")
    fun setImage (image:ImageView , url:String?){
        Picasso.get().load(url ?: emptyImage).into(image)
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