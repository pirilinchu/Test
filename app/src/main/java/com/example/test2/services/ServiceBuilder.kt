package com.example.test2.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private var retrofit: Retrofit? = null
    private var logger: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private var okHttp = OkHttpClient.Builder().addInterceptor(logger).build()

    private fun getClient(baseUrl: String = "https://api.punkapi.com/v2/"): Retrofit {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit as Retrofit
    }

    fun <T> buildService(contract: Class<T>): T {
        return getClient().create(contract)
    }
}