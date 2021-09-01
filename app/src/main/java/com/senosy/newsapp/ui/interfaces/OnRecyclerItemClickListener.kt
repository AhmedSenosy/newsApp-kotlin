package com.senosy.newsapp.ui.interfaces

import com.senosy.newsapp.data.models.ArticleModel


interface OnRecyclerItemClickListener {
    fun onRecyclerItemClickListener(articleData: ArticleModel)
}