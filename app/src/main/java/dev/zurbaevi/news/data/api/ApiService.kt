package dev.zurbaevi.news.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
    }

    @GET("top-headlines?country=us")
    suspend fun getArticles(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse

    @GET("everything")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse

}