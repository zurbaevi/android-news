package dev.zurbaevi.news.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.zurbaevi.news.data.api.ApiService
import dev.zurbaevi.news.data.local.database.ArticleDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiService.BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideTaskDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context.applicationContext,
        ArticleDatabase::class.java,
        "articles_database"
    ).build()

    @Singleton
    @Provides
    fun provideTaskDao(database: ArticleDatabase) = database.articlesDao()

}