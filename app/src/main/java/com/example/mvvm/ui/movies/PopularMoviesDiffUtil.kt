package com.example.mvvm.ui.movies

import androidx.recyclerview.widget.DiffUtil
import com.example.mvvm.model.MoviesResponse

object PopularMoviesDiffUtil : DiffUtil.ItemCallback<MoviesResponse.Movie>() {

    override fun areItemsTheSame(
        oldItem: MoviesResponse.Movie,
        newItem: MoviesResponse.Movie
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: MoviesResponse.Movie,
        newItem: MoviesResponse.Movie
    ): Boolean = oldItem == newItem

}