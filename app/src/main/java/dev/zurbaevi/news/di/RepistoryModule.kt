package dev.zurbaevi.news.di

import dev.zurbaevi.news.data.repository.NewsPagingSource
import dev.zurbaevi.news.data.repository.NewsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { NewsRepository(get(), get()) }
    single { NewsPagingSource(get(), get()) }
}