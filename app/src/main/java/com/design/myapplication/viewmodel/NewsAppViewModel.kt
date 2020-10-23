package com.design.myapplication.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.design.myapplication.api.ApiInterface
import com.design.myapplication.api.Resource
import com.design.myapplication.db.SourceEntity
import com.design.myapplication.model.ArticlesResponse
import com.design.myapplication.repo.NewsRepository

class NewsAppViewModel(application:Application) :AndroidViewModel(application) {
    private val newRepo:NewsRepository= NewsRepository(ApiInterface.getNewApiService())
    val context: Context =application.applicationContext

    fun getNewsSource(language: String?, category: String?, country: String?):LiveData<Resource<List<SourceEntity>>>{
        return newRepo.fetchNewsSource(context,language,category,country)
    }

    fun getNewsArticle(source:String,sortBy:String):LiveData<ArticlesResponse>{
        return newRepo.getNewsArticles(source,sortBy)
    }

}