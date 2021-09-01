package com.senosy.newsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.senosy.newsapp.data.models.ArticleModel
import com.senosy.newsapp.data.repository.ArticleRepository
import com.senosy.newsapp.data.error.ErrorHandling
import com.senosy.newsapp.utils.providers.ResourceProvider
import kotlinx.coroutines.*

class HomeViewModel(
    private val resourceProvider: ResourceProvider,
    private val repo: ArticleRepository
) : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading
    val failure: MutableLiveData<Boolean> = MutableLiveData()
    val isShowRefresh: MutableLiveData<Boolean> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData("")
    private val _popularArticles = MutableLiveData<List<ArticleModel>>()
    val popularArticles: LiveData<List<ArticleModel>> = _popularArticles

    fun oncreate() {
        _loading.value = false
        failure.value = false
        isShowRefresh.value = false
        getArticles()
    }

    fun getArticles() {
        isShowRefresh.value = false
        coroutineScope.launch {
            _loading.postValue(true)
            try {
                val response = repo.getArticles("apple")
                if (response.status == "ok") {
                    withContext(Dispatchers.Main)
                    {
                        _popularArticles.postValue(response.articles)
                        _loading.postValue(false)
                        failure.postValue(false)
                    }
                }
                else{
                    errorMessage.postValue(
                        ErrorHandling(
                            Throwable(),
                            resourceProvider
                        ).errorMsg

                    )
                    _loading.postValue(false)
                    failure.postValue(true)
                }
            }catch (e:Exception){
                errorMessage.postValue(
                    e.cause?.let {
                        ErrorHandling(
                            it,
                            resourceProvider
                        ).errorMsg
                    }
                )
                _loading.postValue(false)
                failure.postValue(true)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
class HomeViewModelProvider (private val resourceProvider: ResourceProvider,
                              private val repo: ArticleRepository) :ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(resourceProvider,repo) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}