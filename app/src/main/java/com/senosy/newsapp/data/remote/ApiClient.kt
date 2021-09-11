package com.senosy.newsapp.data.remote

import com.senosy.newsapp.data.models.ArticleBaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET(EndPoints.HEADLINE)
    suspend fun getArticlesData(@Query("country") country: String?,
                                @Query("category") category: String?): ArticleBaseResponse
}