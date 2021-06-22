package com.androiddevs.mvvmnewsapp.repository


import com.androiddevs.mvvmnewsapp.api.ApiHelper
import com.androiddevs.mvvmnewsapp.api.NewsAPI
import com.androiddevs.mvvmnewsapp.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.models.Article
import javax.inject.Inject


class NewsRepository @Inject constructor(
    val db: ArticleDatabase,private val newsAPI: NewsAPI
) {
    suspend fun getBreakingNews(category: String,countryCode: String, pageNumber: Int) =
        newsAPI.getCategoryNews(category,countryCode,pageNumber)

    suspend fun getUsBreakingNews(countryCode: String, pageNumber: Int) =
        newsAPI.getBreakingNews(countryCode,pageNumber)

    suspend fun getDeBreakingNews(countryCode: String, pageNumber: Int) =
        newsAPI.getBreakingNews(countryCode,pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()
    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        newsAPI.searchForNews(searchQuery, pageNumber)

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)


    suspend fun getSportsNews(category: String, countryCode: String, pageNumber: Int) =
        newsAPI.getCategoryNews("sports","tr",pageNumber)


    suspend fun getTechNews(category: String, countryCode: String, pageNumber: Int) =
        newsAPI.getCategoryNews("technology","tr",pageNumber)


    suspend fun getUsSportsNews(category: String, countryCode: String, pageNumber: Int) =
        newsAPI.getCategoryNews(countryCode,category,pageNumber)


    suspend fun getDeTechNews(category: String, countryCode: String, pageNumber: Int) =
        newsAPI.getCategoryNews(countryCode,category,pageNumber)

    suspend fun getDeSportsNews(category: String, countryCode: String, pageNumber: Int) =
        newsAPI.getCategoryNews(countryCode,category,pageNumber)

    suspend fun getUsTechNews(category: String, countryCode: String, pageNumber: Int) =
        newsAPI.getCategoryNews(countryCode,category,pageNumber)


}