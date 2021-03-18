package com.example.segundaalbo.common.db.remote

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class RetrofitHttpService {

    abstract fun BeerService(): BeerService


    companion object{
        @Volatile
        private var INSTANCE: BeerService? = null

        private val BASE_URL = "https://api.punkapi.com/v2/"

        fun getInstance():BeerService =
            INSTANCE?: synchronized(this){ INSTANCE?: getRetrofitHttp().create(BeerService::class.java).also{INSTANCE = it } }

        private fun getRetrofitHttp(): Retrofit{
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()
            val retrofit = retrofit(client)

            return retrofit
        }

        private fun retrofit(client:OkHttpClient) =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

    }


}