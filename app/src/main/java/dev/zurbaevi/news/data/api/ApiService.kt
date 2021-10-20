package dev.zurbaevi.news.data.api

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    companion object {
        const val BASE_URL = "https://saurav.tech/NewsAPI/"
    }

    @GET("everything/cnn.json")
    suspend fun getArticles(): ApiResponse

}