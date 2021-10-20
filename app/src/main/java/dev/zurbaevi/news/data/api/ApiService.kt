package dev.zurbaevi.news.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "286c49083f604b6db2bcb86c98f1cbeb"
    }

    @GET("top-headlines?country=us&apiKey=$API_KEY")
    suspend fun getArticles(@Query("page") page: Int): ApiResponse

}