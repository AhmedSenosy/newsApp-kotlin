package com.senosy.newsapp.data.remote

import com.senosy.newsapp.data.models.ArticleBaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    @GET(EndPoints.GETEVERYTHING)
    suspend fun getArticlesData(@Query("q") period: String?): ArticleBaseResponse
}