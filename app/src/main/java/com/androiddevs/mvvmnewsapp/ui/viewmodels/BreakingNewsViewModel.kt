package com.androiddevs.mvvmnewsapp.ui.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.models.NewsResponse
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.util.Resource
import kotlinx.coroutines.launch


import androidx.lifecycle.AndroidViewModel

import retrofit2.Response


class BreakingNewsViewModel @ViewModelInject constructor(
    app: Application,
    private val newsRepository: NewsRepository
) : AndroidViewModel(app){
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse?=null
    val usBreakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var usBreakingNewsPage = 1
    var usBreakingNewsResponse: NewsResponse?=null

    val deBreakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var deBreakingNewsPage = 1
    var deBreakingNewsResponse: NewsResponse?=null
    init {
        getBreakingNews("tr","business")
        getUsBreakingNews("us")
        getDeBreakingNews("de")
    }
    fun getBreakingNews(countryCode: String,category:String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, category,breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }
    fun getUsBreakingNews(countryCode: String) = viewModelScope.launch {
        usBreakingNews.postValue(Resource.Loading())
        val response = newsRepository.getUsBreakingNews(countryCode, usBreakingNewsPage)
        usBreakingNews.postValue(handleUsBreakingNewsResponse(response))
    }
    fun getDeBreakingNews(countryCode: String) = viewModelScope.launch {
        deBreakingNews.postValue(Resource.Loading())
        val response = newsRepository.getDeBreakingNews(countryCode, deBreakingNewsPage)
        deBreakingNews.postValue(handleDeBreakingNewsResponse(response))
    }
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++

                if(breakingNewsResponse==null){
                    breakingNewsResponse=resultResponse
                }
                else{
                    val oldArticles=breakingNewsResponse?.articles
                    val newArticles=resultResponse.articles
                    oldArticles?.addAll(newArticles)

                }
                return Resource.Success(breakingNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleUsBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                usBreakingNewsPage++

                if(usBreakingNewsResponse==null){
                    usBreakingNewsResponse=resultResponse
                }
                else{
                    val oldArticles=usBreakingNewsResponse?.articles
                    val newArticles=resultResponse.articles
                    oldArticles?.addAll(newArticles)

                }
                return Resource.Success(usBreakingNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleDeBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                deBreakingNewsPage++

                if(deBreakingNewsResponse==null){
                    deBreakingNewsResponse=resultResponse
                }
                else{
                    val oldArticles=deBreakingNewsResponse?.articles
                    val newArticles=resultResponse.articles
                    oldArticles?.addAll(newArticles)

                }
                return Resource.Success(deBreakingNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}