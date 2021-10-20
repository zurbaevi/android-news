package dev.zurbaevi.news.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.zurbaevi.news.data.api.ApiResponse
import dev.zurbaevi.news.data.repository.NewsRepository
import dev.zurbaevi.news.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    private val _articles = MutableLiveData<Resource<ApiResponse>>()
    val articles get() = _articles

    init {
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
            _articles.value = Resource.loading(null)
            try {
                _articles.value = Resource.success(newsRepository.getArticles())
            } catch (exception: Exception) {
                _articles.value = Resource.error(null, exception.message ?: "Error Occurred!")
            }
        }
    }

}