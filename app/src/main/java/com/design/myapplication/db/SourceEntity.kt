package com.design.myapplication.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.design.myapplication.NewsConstant

@Entity(tableName = NewsConstant.T_SOURCE)
class SourceEntity(
    @PrimaryKey var id: String = ""
    , var name: String? = ""
    , var description: String? = ""
    , var url: String? = ""
    , var category: String? = ""
    , var language: String? = ""
    , var country: String? = ""
)