package dev.zurbaevi.news.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.zurbaevi.news.data.api.ApiService
import dev.zurbaevi.news.data.model.Articles
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource(private val apiService: ApiService) : PagingSource<Int, Articles>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Articles> {
        val pageIndex = params.key ?: 1
        return try {
            val data = apiService.getArticles(
                pageIndex,
            )
            LoadResult.Page(
                data = data.articles,
                prevKey = if (params.key == 1) null else pageIndex - 1,
                nextKey = if (data.articles.isEmpty()) null else pageIndex + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Articles>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}