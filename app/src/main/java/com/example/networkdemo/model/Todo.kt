package com.example.networkdemo.model

//This class represents a single "To-do" item returned by the API.
data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)