package com.design.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.design.myapplication.NewsConstant

@Dao
interface SourceDoa{

    @Query("SELECT * FROM " + NewsConstant.T_SOURCE)
    fun getAllNewsSource(): LiveData<List<SourceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSources(source: List<SourceEntity>)

    @Delete
    fun deleteSource(source: List<SourceEntity>)
}