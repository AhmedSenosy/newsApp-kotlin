package com.senosy.newsapp.utils.providers

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes
import androidx.core.util.Preconditions


class ResourceProvider(context: Context) : BaseResourceProvider {


    @SuppressLint("RestrictedApi")
    private val mContext: Context = Preconditions.checkNotNull(
        context,
        "context cannot be null"
    )

    override fun getString(@StringRes id: Int): String {
        return mContext.resources.getString(id)
    }


    override fun getString(@StringRes resId: Int, vararg formatArgs: String?): String {
        return mContext.resources.getString(resId, *formatArgs)
    }

}