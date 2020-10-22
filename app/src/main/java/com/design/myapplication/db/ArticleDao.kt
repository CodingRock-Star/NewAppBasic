package com.design.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.design.myapplication.NewsConstant
import com.google.android.material.tabs.TabLayoutMediator

@Dao
interface ArticleDao {
    @Query("SELECT * FROM " + NewsConstant.T_ARTICLE)
    fun queryArticle(): LiveData<ArticleEntity>

    //add the new entity on the table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArticle(articleList: Array<ArticleEntity>)

    //remove the article from the table
    @Delete
    fun deleteArticle(articleList: Array<ArticleEntity>)
}