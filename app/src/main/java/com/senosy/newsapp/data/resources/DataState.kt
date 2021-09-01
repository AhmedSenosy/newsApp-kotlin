package com.senosy.newsapp.data.resources

sealed class DataState<out R>{
    data class Success<out T>(val data : T) : DataState<T>()
    data class Error(val error: Exception?) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}
