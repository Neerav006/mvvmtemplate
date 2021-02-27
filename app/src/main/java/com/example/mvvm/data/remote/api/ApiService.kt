package com.example.mvvm.data.remote.api

import com.example.mvvm.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    // temp test get request
    @GET("/DummyFoodiumApi/api/posts/")
    suspend fun getPosts(): Response<List<Post>>
}