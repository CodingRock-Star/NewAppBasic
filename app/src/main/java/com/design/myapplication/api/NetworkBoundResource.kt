package com.design.myapplication.api

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


abstract class NetworkBoundResource<ResultType, RequestType> @MainThread constructor() {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        val dbSource = loadFromDb()
        result.addSource(dbSource){
            resultType: ResultType ->
            result.removeSource(dbSource)
            if(shouldFetch(resultType)){
              fetchFromNetwork(dbSource)
            }else{
               result.addSource(dbSource){
                   it->result.value=Resource.success(it)
               }
            }
        }
    }

     fun fetchFromNetwork(dbSource: LiveData<ResultType>){
         val apiResponse = createCall()
         // we re-attach dbSource as a new source, it will dispatch its latest value quickly
         result.addSource(dbSource) { resultType ->
             result.value = Resource.loading(resultType)
         }

         result.addSource(apiResponse) { response ->
             result.removeSource(apiResponse)
             result.removeSource(dbSource)

             if (response!!.isSuccessful) {
                 processResponse(response).let {
                     val subscribe: Any = Observable.fromCallable { saveCallResult(it!!) }
                         .subscribeOn(Schedulers.io())
                         .subscribe()
                     subscribe
                 }
                 // we specially request a new live data,
                 // otherwise we will get immediately last cached value,
                 // which may not be updated with latest results received from network.
                 result.addSource(loadFromDb()) { resultType -> result.value = Resource.success(resultType) }

             } else {
                 onFetchFailed()
                 result.addSource(dbSource
                 ) { resultType -> result.value = response.errorMessage?.let { Resource.error(resultType, it) } }
             }
         }
     }

    protected open fun onFetchFailed() {}

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    @WorkerThread
    private fun processResponse(response: ApiResponse<RequestType>): RequestType? {
        return response.body
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}