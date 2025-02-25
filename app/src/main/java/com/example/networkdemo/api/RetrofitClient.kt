package com.example.networkdemo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//This creates a singleton instance of Retrofit, so we don’t have to initialize it multiple times.
object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}