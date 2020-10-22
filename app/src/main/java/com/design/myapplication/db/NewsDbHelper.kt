package com.design.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.design.myapplication.NewsConstant

@Database(entities = arrayOf(SourceEntity::class, ArticleEntity::class), version = 1)
abstract class NewsDbHelper : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
    abstract fun getSourceDao(): SourceDoa

    companion object {
        private var db: NewsDbHelper? = null
        fun getInstance(context: Context): NewsDbHelper {
            if (db == null) {
                db = Room.databaseBuilder(context, NewsDbHelper::class.java, NewsConstant.DB_NAME)
                    .build()
            }
            return db!!
        }

    }
}