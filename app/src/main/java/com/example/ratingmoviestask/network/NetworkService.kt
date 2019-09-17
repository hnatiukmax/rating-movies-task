package com.example.ratingmoviestask.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {


    companion object {
        private val mInstance : NetworkService? = null

        fun getInstance() = if (mInstance == null) NetworkService() else mInstance
    }

    private lateinit var mRetrofit : Retrofit

    private constructor() {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    public fun getTaskApi() = mRetrofit.create(MoviesAPI::class.java)
}
