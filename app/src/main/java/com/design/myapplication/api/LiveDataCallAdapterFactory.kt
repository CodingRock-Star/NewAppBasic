package com.design.myapplication.api

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}