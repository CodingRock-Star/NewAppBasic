package com.design.myapplication.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.design.myapplication.NewsConstant

@Entity(tableName = NewsConstant.T_ARTICLE)
class ArticleEntity(
    @PrimaryKey var tile: String = ""
    , var source: String? = ""
    , var author: String? = ""
    , var description: String? = ""
    , var url: String? = ""
    , var urlToImage: String? = ""
    , var publishedAt: String? = ""
)