package dev.zurbaevi.news.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.zurbaevi.news.data.model.Articles
import dev.zurbaevi.news.data.repository.NewsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    private val _articles = MutableLiveData<PagingData<Articles>>()
    val articles get() = _articles

    init {
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
            newsRepository.getArticles()
                .cachedIn(viewModelScope)
                .collect {
                    _articles.value = it
                }
        }
    }

    fun searchArticles(query: String) {
        viewModelScope.launch {
            newsRepository.searchArticles(query)
                .cachedIn(viewModelScope)
                .collect {
                    _articles.value = it
                }
        }
    }

}