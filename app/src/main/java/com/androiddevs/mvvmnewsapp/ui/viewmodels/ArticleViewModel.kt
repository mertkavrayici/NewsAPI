package com.androiddevs.mvvmnewsapp.ui.viewmodels


import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.models.Article
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import kotlinx.coroutines.launch

class ArticleViewModel  @ViewModelInject constructor(
    app: Application,
    private val newsRepository: NewsRepository

) : AndroidViewModel(app) {
    fun saveArticle(article: Article)=viewModelScope.launch {
        newsRepository.upsert(article)
    }

}