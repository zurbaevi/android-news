package dev.zurbaevi.news.data.repository

import dev.zurbaevi.news.data.api.ApiService
import dev.zurbaevi.news.data.local.dao.ArticlesDao
import dev.zurbaevi.news.data.model.Articles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val apiService: ApiService,
    private val articlesDao: ArticlesDao
) {

    suspend fun getArticles() = withContext(Dispatchers.IO) {
        apiService.getArticles()
    }

    suspend fun insert(articles: Articles) = withContext(Dispatchers.IO) {
        articlesDao.insert(articles)
    }

    suspend fun getFavoritesArticles() = withContext(Dispatchers.IO) {
        articlesDao.getArticles()
    }

    suspend fun searchArticles(query: String) = withContext(Dispatchers.IO) {
        apiService.searchArticles(query)
    }

}