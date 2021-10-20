package dev.zurbaevi.news.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.zurbaevi.news.data.local.dao.ArticlesDao
import dev.zurbaevi.news.data.model.Articles

@Database(entities = [Articles::class], version = 1, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao

}