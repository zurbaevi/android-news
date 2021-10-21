package dev.zurbaevi.news.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "b2f3da247c1442e5805fd0c4457b7813"
    }

    @GET("top-headlines?country=us&apiKey=$API_KEY")
    suspend fun getArticles(): ApiResponse

    @GET("everything?apiKey=$API_KEY")
    suspend fun searchArticles(@Query("q") query: String): ApiResponse

}