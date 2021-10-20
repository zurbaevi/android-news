package dev.zurbaevi.news.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.zurbaevi.news.data.model.Articles
import dev.zurbaevi.news.data.repository.NewsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    private val _articles = MutableLiveData<List<Articles>>()
    val articles get() = _articles

    init {
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
            _articles.postValue(newsRepository.getArticlesFavorites())
        }
    }

    fun insert(articles: Articles) {
        viewModelScope.launch {
            newsRepository.insert(articles)
        }
    }

}