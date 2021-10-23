package dev.zurbaevi.news.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zurbaevi.news.data.model.Articles
import dev.zurbaevi.news.data.repository.NewsRepository
import kotlinx.coroutines.launch

class DetailsViewModel constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    fun insert(articles: Articles) {
        viewModelScope.launch {
            newsRepository.insert(articles)
        }
    }

}