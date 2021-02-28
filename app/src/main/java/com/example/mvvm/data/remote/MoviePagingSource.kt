package com.example.mvvm.data.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvm.data.repository.MovieRepository
import com.example.mvvm.data.repository.PostRepository
import com.example.mvvm.data.repository.Resource
import com.example.mvvm.model.MoviesResponse
import com.example.mvvm.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val repository: MovieRepository
) : PagingSource<Int, MoviesResponse.Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesResponse.Movie> {

        return try {
            val nextPage = params.key ?: 1
            val movieListResponse = repository.getPopularMovies(nextPage)

            LoadResult.Page(
                data = movieListResponse!!.results,
                prevKey = if (nextPage == 1) null else nextPage - 1 ,
                nextKey = if (nextPage < movieListResponse!!.total)
                    movieListResponse.page?.plus(1) else null
            )


        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MoviesResponse.Movie>): Int? {
       return  state.anchorPosition
    }


}