package com.senosy.newsapp.data.models

import com.google.gson.annotations.SerializedName

data class ArticleBaseResponse (
    @SerializedName("status") var status : String,
    @SerializedName("totalResults") var totalResults : Int,
    @SerializedName("articles") var articles : List<ArticleModel>
        )