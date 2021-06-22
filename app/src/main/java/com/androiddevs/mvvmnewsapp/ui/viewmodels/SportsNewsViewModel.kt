package com.androiddevs.mvvmnewsapp.ui.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.models.NewsResponse
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class SportsNewsViewModel @ViewModelInject constructor(
    app: Application,
    private val newsRepository: NewsRepository

) : AndroidViewModel(app) {
    val deSportsNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var deSportsNewsPage = 1
    var deSportsNewsResponse: NewsResponse?=null

    val usSportsNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var usSportsNewsPage = 1
    var usSportsNewsResponse:NewsResponse?=null

    val sportsNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var sportsNewsPage=1
    var sportsNewsResponse:NewsResponse?=null
    init {
        getDeSportsNews("sports","de")
        getSportsNews("sports","tr")
        getUsSportsNews("sports","us")
    }
    fun getUsSportsNews(category: String,countryCode: String) = viewModelScope.launch {
        usSportsNews.postValue(Resource.Loading())
        val response = newsRepository.getUsSportsNews(category,countryCode, usSportsNewsPage)
        usSportsNews.postValue(handleUsSportsNewsResponse(response))
    }
    fun getDeSportsNews(category: String,countryCode: String) = viewModelScope.launch {
        deSportsNews.postValue(Resource.Loading())
        val response = newsRepository.getDeSportsNews(category,countryCode, deSportsNewsPage)
        deSportsNews.postValue(handleDeSportsNewsResponse(response))
    }
    fun getSportsNews(category: String,countryCode: String)=viewModelScope.launch {
        sportsNews.postValue(Resource.Loading())
        val response=newsRepository.getSportsNews(category,countryCode,sportsNewsPage)
        sportsNews.postValue(handleSportsNewsResponse(response))
    }
    private fun handleUsSportsNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                usSportsNewsPage++

                if(usSportsNewsResponse==null){
                    usSportsNewsResponse=resultResponse
                }
                else{
                    val oldArticles=usSportsNewsResponse?.articles
                    val newArticles=resultResponse.articles
                    oldArticles?.addAll(newArticles)

                }
                return Resource.Success(usSportsNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleDeSportsNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                deSportsNewsPage++

                if(deSportsNewsResponse==null){
                    deSportsNewsResponse=resultResponse
                }
                else{
                    val oldArticles=deSportsNewsResponse?.articles
                    val newArticles=resultResponse.articles
                    oldArticles?.addAll(newArticles)

                }
                return Resource.Success(deSportsNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleSportsNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse->
                sportsNewsPage++

                if (sportsNewsResponse==null){
                    sportsNewsResponse=resultResponse
                }
                else{
                    val oldArticles=sportsNewsResponse?.articles
                    val newArticles=resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return  Resource.Success(sportsNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}