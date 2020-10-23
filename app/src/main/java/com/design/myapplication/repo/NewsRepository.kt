package com.design.myapplication.repo

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.design.myapplication.BuildConfig
import com.design.myapplication.api.ApiInterface
import com.design.myapplication.api.ApiResponse
import com.design.myapplication.api.NetworkBoundResource
import com.design.myapplication.api.Resource
import com.design.myapplication.db.NewsDbHelper
import com.design.myapplication.db.SourceEntity
import com.design.myapplication.model.ArticlesResponse
import com.design.myapplication.model.SourceResponse
import org.intellij.lang.annotations.Language
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class NewsRepository(private val apiInterface:ApiInterface){
    val repoRateLimiter = RateLimiter<String>(10, TimeUnit.MINUTES)
    fun fetchNewsSource(context: Context, language: String?, category: String?, country: String?): LiveData<Resource<List<SourceEntity>>> {
        return object : NetworkBoundResource<List<SourceEntity>, SourceResponse>() {
            override fun onFetchFailed() {
                repoRateLimiter.reset("all")
            }

            override fun saveCallResult(item: SourceResponse) {
//                To avoid this make API response pojo class as entity
                var sourceList = ArrayList<SourceEntity>()
                item.sources.forEach {
                    var sourceEntity = SourceEntity()
                    sourceEntity.id = it.id.toString()
                    sourceEntity.category = it.category
                    sourceEntity.country = it.country
                    sourceEntity.description = it.description
                    sourceEntity.language = it.language
                    sourceEntity.name = it.name
                    sourceEntity.url = it.url
                    sourceList.add(sourceEntity)
                }
                NewsDbHelper.getInstance(context).getSourceDao().insertSources(sourceList)
            }

            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun shouldFetch(data: List<SourceEntity>?): Boolean = repoRateLimiter.shouldFetch("all")

            override fun loadFromDb(): LiveData<List<SourceEntity>> {
                return NewsDbHelper.getInstance(context).getSourceDao().getAllNewsSource()
            }

            override fun createCall(): LiveData<ApiResponse<SourceResponse>> {
                return apiInterface.getSources(language, category, country)
            }
        }.asLiveData()
    }
    fun getNewsArticles(source: String, sortBy: String?): LiveData<ArticlesResponse> {
        val liveDataArticlesResponse: MutableLiveData<ArticlesResponse> = MutableLiveData()
        apiInterface.getArticles(source, sortBy, BuildConfig.API_KEY).enqueue(object :
            Callback<ArticlesResponse> {
            override fun onFailure(call: Call<ArticlesResponse>?, t: Throwable?) {
                Log.e("Oops", "Network error ${t?.message}")
            }

            override fun onResponse(call: Call<ArticlesResponse>, response: Response<ArticlesResponse>) {
                Log.e("Article Call Status", response.body()?.status)
                Log.e("List Contains", "${response.body()?.articles?.size}")
                liveDataArticlesResponse.value = response.body()
            }
        })
        return liveDataArticlesResponse
    }

}