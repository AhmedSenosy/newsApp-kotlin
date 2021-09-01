package com.senosy.newsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
//        periodValue.set(PERIOD_VALUE)
//        adapter.addListener(object : OnRecyclerItemClickListener {
//            override fun onRecyclerItemClickListener(articleData: ArticleData) {
//                articleDetails.value = articleData
//            }
//
//        })
        getArticles()
    }

    private fun getArticles() {
        isShowRefresh.value = false
        coroutineScope.launch {
            _loading.postValue(true)
            try {
                val response = repo.getArticles("")
                if (response.status == "success") {
                    withContext(Dispatchers.Main)
                    {
                        _popularArticles.value = response.articles
                        _loading.postValue(false)
                        failure.postValue(false)
                    }
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

//            useCase.getMostPopular(period).collect { it ->
//                when (it) {
//                    is DataState.Loading -> {
//                        _loading.postValue(true)
//                    }
//
//                    is DataState.Success -> {
//                        withContext(Dispatchers.Main)
//                        {
//                            _popularArticles.value = it.data
//                            _loading.postValue(false)
//                            failure.postValue(false)
//                            it.data.articleData?.let { it1 -> adapter.addData(it1) }
//                        }
//                    }
//
//                    is DataState.Error -> {
//                        errorMessage.set(it.error?.let { it1 ->
//                            ErrorHandling(
//                                it1,
//                                resourceProvider
//                            ).errorMsg
//                        })
//                        _loading.postValue(false)
//                        failure.postValue(true)
//                    }
//                }
//
//            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}