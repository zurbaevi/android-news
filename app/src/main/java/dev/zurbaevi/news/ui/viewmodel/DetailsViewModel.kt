package dev.zurbaevi.news.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.zurbaevi.news.data.model.Articles
import dev.zurbaevi.news.data.repository.NewsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    fun insert(articles: Articles) {
        viewModelScope.launch {
            newsRepository.insert(articles)
        }
    }

}