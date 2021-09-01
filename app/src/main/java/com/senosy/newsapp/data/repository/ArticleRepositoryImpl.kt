package com.senosy.newsapp.data.repository
import android.content.Context
import com.senosy.newsapp.data.models.ArticleBaseResponse
import com.senosy.newsapp.data.remote.ApiClient

class ArticleRepositoryImpl (
    private val apiClient: ApiClient
) : ArticleRepository {
    override suspend fun getArticles(tags: String?): ArticleBaseResponse {
       return apiClient.getArticlesData(tags);
    }


}