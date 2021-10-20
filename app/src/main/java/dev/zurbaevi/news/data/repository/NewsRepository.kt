package dev.zurbaevi.news.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import dev.zurbaevi.news.data.api.ApiService
import dev.zurbaevi.news.data.local.dao.ArticlesDao
import dev.zurbaevi.news.data.model.Articles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService,
    private val articlesDao: ArticlesDao
) {

    fun getArticles() =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(apiService) }
        ).flow


    suspend fun insert(articles: Articles) = withContext(Dispatchers.IO) {
        articlesDao.insert(articles)
    }

    suspend fun getArticlesFavorites() = withContext(Dispatchers.IO) {
        articlesDao.getArticles()
    }

}