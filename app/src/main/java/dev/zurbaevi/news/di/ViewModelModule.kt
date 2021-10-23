package dev.zurbaevi.news.di

import dev.zurbaevi.news.ui.viewmodel.DetailsViewModel
import dev.zurbaevi.news.ui.viewmodel.FavoriteViewModel
import dev.zurbaevi.news.ui.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}