package com.example.mvvm.ui.movies

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.R
import com.example.mvvm.data.session.SessionManager
import com.example.mvvm.databinding.MovieListFragmentBinding
import com.example.mvvm.ui.base.BaseFragment
import com.example.mvvm.ui.base.LoadingStateAdapter
import com.example.mvvm.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : BaseFragment<MovieListViewModel,MovieListFragmentBinding>() {

    override val mViewModel: MovieListViewModel by viewModels()
    @Inject
    lateinit var sessionManager: SessionManager
    lateinit var mViewBinding:MovieListFragmentBinding

    private lateinit var movieAdapter:PopularMoviesAdapter

    companion object {
        fun newInstance() = MovieListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieAdapter = PopularMoviesAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = MovieListFragmentBinding.inflate(inflater,container,false)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewBinding.popularMoviesList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter { movieAdapter.retry() }
            )
        }

        lifecycleScope.launch {
            mViewModel.movies.collectLatest {
                movieAdapter.submitData(it)
            }
        }

        mViewBinding.retryButton.setOnClickListener{
            movieAdapter.retry()
        }

        movieAdapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {

                mViewBinding.retryButton.visibility = View.GONE

                // Show ProgressBar
                mViewBinding.progressBar.visibility = View.VISIBLE
            }
            else {
                // Hide ProgressBar
                mViewBinding.progressBar.visibility = View.GONE

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> {
                        mViewBinding.retryButton.visibility = View.VISIBLE
                        loadState.refresh as LoadState.Error
                    }
                    else -> null
                }
                errorState?.let {
                    requireActivity().showToast(it.error.message+"")
                }
            }
        }
    }
    }
