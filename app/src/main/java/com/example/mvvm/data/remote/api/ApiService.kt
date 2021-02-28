package com.example.mvvm.data.remote.api

import com.example.mvvm.model.MoviesResponse
import com.example.mvvm.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // temp test get request
    @GET("/DummyFoodiumApi/api/posts/")
    suspend fun getPosts(): Response<List<Post>>

    @GET("popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): Response<MoviesResponse>
}