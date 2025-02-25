package com.example.networkdemo.api

import retrofit2.http.GET
import retrofit2.Call
import com.example.networkdemo.model.Todo

//This defines how we fetch data from https://jsonplaceholder.typicode.com/todos/1.
interface ApiService {
    @GET("todos/1") // Fetch a single to-do item
    fun getTodo(): Call<Todo>
}