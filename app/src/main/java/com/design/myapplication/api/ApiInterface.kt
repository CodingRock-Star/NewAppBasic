package com.design.myapplication.api

import androidx.lifecycle.LiveData
import com.design.myapplication.NewsConstant
import com.design.myapplication.model.ArticlesResponse
import com.design.myapplication.model.SourceResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface{
    companion object{

        fun getNewApiService():ApiInterface{
            return Retrofit.Builder()
                .baseUrl(NewsConstant.NEWS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build().create(ApiInterface::class.java)
        }


    }
    @GET("sources")
    fun getSources(@Query("language") language: String?,
                   @Query("category") category: String?,
                   @Query("country") country: String?): LiveData<ApiResponse<SourceResponse>>

    @GET("articles")
    fun getArticles(@Query("source") source: String,
                    @Query("sortBy") sortBy: String?,
                    @Query("apiKey") apiKey: String): Call<ArticlesResponse>
}