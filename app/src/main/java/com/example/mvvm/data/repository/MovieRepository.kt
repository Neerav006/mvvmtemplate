package com.example.mvvm.data.repository

import com.example.mvvm.data.remote.api.ApiService
import com.example.mvvm.data.remote.api.BaseService
import com.example.mvvm.model.MoviesResponse
import com.example.mvvm.model.Result
import com.example.mvvm.utils.AppConstants
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: ApiService
) : BaseService() {

    suspend fun fetchPopularMovies(page: Int) : Result<MoviesResponse> {
        return createCall { apiService.getPopularMovies(AppConstants.API_KEY,page) }
    }

    suspend fun getPopularMovies(page: Int) : MoviesResponse {
        return when(val result = fetchPopularMovies(page)){
            is Result.Success -> result.data
            is Result.Error -> throw result.error
        }
    }
}