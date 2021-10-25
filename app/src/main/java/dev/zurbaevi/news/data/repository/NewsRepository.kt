package dev.zurbaevi.news.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.zurbaevi.news.data.api.ApiService
import dev.zurbaevi.news.data.local.dao.ArticlesDao
import dev.zurbaevi.news.data.model.Articles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository constructor(
    private val apiService: ApiService,
    private val articlesDao: ArticlesDao
) {

    suspend fun getArticles() = withContext(Dispatchers.IO) {
        Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(apiService, null) }
        ).flow
    }

    suspend fun searchArticles(query: String) = withContext(Dispatchers.IO) {
        Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(apiService, query) }
        ).flow
    }

    suspend fun insert(articles: Articles) = withContext(Dispatchers.IO) {
        articlesDao.insert(articles)
    }

    suspend fun getFavoritesArticles() = withContext(Dispatchers.IO) {
        articlesDao.getArticles()
    }

}