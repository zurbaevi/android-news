package dev.zurbaevi.news.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.zurbaevi.news.data.api.ApiService
import dev.zurbaevi.news.data.model.Articles
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource(
    private val apiService: ApiService,
    private val query: String?
) : PagingSource<Int, Articles>() {

    override fun getRefreshKey(state: PagingState<Int, Articles>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Articles> {
        val position = params.key ?: 1
        return try {
            val response = if (query != null) {
                apiService.searchArticles(query, position, params.loadSize)
            } else {
                apiService.getArticles(position, params.loadSize)
            }
            val photos = response.articles
            LoadResult.Page(
                data = photos,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}