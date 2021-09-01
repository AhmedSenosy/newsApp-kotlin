package com.senosy.newsapp.data.repository

import com.senosy.newsapp.data.models.ArticleBaseResponse

interface ArticleRepository {
    suspend fun getArticles(tags:String?): ArticleBaseResponse
}