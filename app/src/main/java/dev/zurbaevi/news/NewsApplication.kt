package dev.zurbaevi.news

import android.app.Application
import dev.zurbaevi.news.di.appModule
import dev.zurbaevi.news.di.repositoryModule
import dev.zurbaevi.news.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(listOf(appModule, repositoryModule, viewModelModule))
        }
    }

}