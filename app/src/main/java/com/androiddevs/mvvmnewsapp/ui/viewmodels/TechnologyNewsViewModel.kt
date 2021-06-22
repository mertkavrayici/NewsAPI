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

class TechnologyNewsViewModel  @ViewModelInject constructor(
    app: Application,
    private val newsRepository: NewsRepository

) : AndroidViewModel(app)  {
    val deTechNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var deTechNewsPage = 1
    var deTechNewsResponse: NewsResponse?=null

    val usTechNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var usTechNewsPage = 1
    var usTechNewsResponse:NewsResponse?=null

    val techNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var techNewsPage=1
    var techNewsResponse:NewsResponse?=null
    init {

        getTechNews("technology", "tr")

        getUsTechNews("technology", "us")

        getDeTechNews("technology", "de")

    }
    fun getUsTechNews(category: String,countryCode: String) = viewModelScope.launch {
        usTechNews.postValue(Resource.Loading())
        val response = newsRepository.getUsTechNews(category,countryCode, usTechNewsPage)
        usTechNews.postValue(handleUsTechNewsResponse(response))
    }
    fun getDeTechNews(category: String,countryCode: String) = viewModelScope.launch {
        deTechNews.postValue(Resource.Loading())
        val response = newsRepository.getDeTechNews(category,countryCode, deTechNewsPage)
        deTechNews.postValue(handleDeTechNewsResponse(response))
    }
    fun getTechNews(category: String,countryCode: String)=viewModelScope.launch {
        techNews.postValue(Resource.Loading())
        val response=newsRepository.getTechNews(category,countryCode,techNewsPage)
        techNews.postValue(handleTechNewsResponse(response))
    }
    private fun handleUsTechNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                usTechNewsPage++

                if(usTechNewsResponse==null){
                    usTechNewsResponse=resultResponse
                }
                else{
                    val oldArticles=usTechNewsResponse?.articles
                    val newArticles=resultResponse.articles
                    oldArticles?.addAll(newArticles)

                }
                return Resource.Success(usTechNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleDeTechNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                deTechNewsPage++

                if(deTechNewsResponse==null){
                    deTechNewsResponse=resultResponse
                }
                else{
                    val oldArticles=deTechNewsResponse?.articles
                    val newArticles=resultResponse.articles
                    oldArticles?.addAll(newArticles)

                }
                return Resource.Success(deTechNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleTechNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse->
                techNewsPage++

                if (techNewsResponse==null){
                    techNewsResponse=resultResponse
                }
                else{
                    val oldArticles=techNewsResponse?.articles
                    val newArticles=resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return  Resource.Success(techNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    }


