package dev.zurbaevi.news.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.zurbaevi.news.BuildConfig
import dev.zurbaevi.news.data.api.ApiService
import dev.zurbaevi.news.data.local.database.ArticleDatabase
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor =
        Interceptor { chain ->
            val original = chain.request()
            val httpUrl = original.url.newBuilder()
                .addQueryParameter("apiKey", BuildConfig.API_KEY)
                .build()
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(httpUrl)
            chain.proceed(requestBuilder.build())
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(header: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                addInterceptor(header)
            }.build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
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