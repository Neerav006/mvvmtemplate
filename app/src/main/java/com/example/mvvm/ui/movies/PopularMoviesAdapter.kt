package com.example.mvvm.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.mvvm.databinding.ListItemMoviesBinding
import com.example.mvvm.model.MoviesResponse
import com.example.mvvm.ui.base.BaseViewHolder

class PopularMoviesAdapter :
    PagingDataAdapter<MoviesResponse.Movie, PopularMoviesAdapter.ViewHolder>(PopularMoviesDiffUtil) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindItem(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ListItemMoviesBinding) :
        BaseViewHolder<MoviesResponse.Movie>(binding.root) {
        override fun bindItem(item: MoviesResponse.Movie?) {
            binding.movie = item
            binding.viewHolder = this
            onItemClick(item)
            binding.executePendingBindings()
        }

        private fun onItemClick(item: MoviesResponse.Movie?) {
            binding.clickListener = View.OnClickListener {
                if (item != null) {

                }
            }
        }
    }
}