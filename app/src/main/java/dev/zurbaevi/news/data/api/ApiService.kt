package dev.zurbaevi.news.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "0108b67107494d83933ee74399242499"
    }

    @GET("top-headlines?country=us&apiKey=$API_KEY")
    suspend fun getArticles(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse

    @GET("everything?apiKey=$API_KEY")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse

}