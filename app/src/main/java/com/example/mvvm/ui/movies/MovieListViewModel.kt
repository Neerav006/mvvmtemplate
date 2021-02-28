package com.example.mvvm.ui.movies

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.mvvm.data.remote.MoviePagingSource
import com.example.mvvm.data.repository.MovieRepository
import com.example.mvvm.model.MoviesResponse
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var movieRepository:MovieRepository

    val movies: Flow<PagingData<MoviesResponse.Movie>> = getMovieListStream()
        .map { pagingData -> pagingData.map { it } }




    private fun getMovieListStream(): Flow<PagingData<MoviesResponse.Movie>> {
        return Pager(PagingConfig(20)) {
            MoviePagingSource(movieRepository)
        }.flow
    }
}

