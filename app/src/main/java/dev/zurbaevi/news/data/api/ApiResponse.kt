package dev.zurbaevi.news.data.api

import dev.zurbaevi.news.data.model.Articles

data class ApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Articles>
)