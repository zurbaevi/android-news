package dev.zurbaevi.news.di

import android.content.Context
import androidx.room.Room
import dev.zurbaevi.news.BuildConfig
import dev.zurbaevi.news.data.api.ApiService
import dev.zurbaevi.news.data.local.database.ArticleDatabase
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { provideInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }
    single { provideTaskDatabase(get()) }
    single { provideTaskDao(get()) }
}

private fun provideInterceptor(): Interceptor =
    Interceptor { chain ->
        val original = chain.request()
        val httpUrl = original.url.newBuilder()
            .addQueryParameter("apiKey", BuildConfig.API_KEY)
            .build()
        val requestBuilder: Request.Builder = original.newBuilder()
            .url(httpUrl)
        chain.proceed(requestBuilder.build())
    }

private fun provideOkHttpClient(header: Interceptor): OkHttpClient =
    OkHttpClient.Builder()
        .apply {
            addInterceptor(header)
        }.build()

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(ApiService.BASE_URL)
        .build()
}

private fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

private fun provideTaskDatabase(context: Context) = Room.databaseBuilder(
    context.applicationContext,
    ArticleDatabase::class.java,
    "articles_database"
).build()

private fun provideTaskDao(database: ArticleDatabase) = database.articlesDao()