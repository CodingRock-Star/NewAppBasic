package com.design.myapplication.api

import com.design.myapplication.NewsConstant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
}