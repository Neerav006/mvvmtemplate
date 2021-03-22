package com.example.mvvm.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.mvvm.R
import com.example.mvvm.databinding.MovieDetailFragmentBinding
import com.example.mvvm.ui.base.BaseFragment
import com.example.mvvm.ui.movies.DataBindingAdapter
import com.example.mvvm.utils.setImage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<MovieDetailViewModel,MovieDetailFragmentBinding>() {
    override val mViewModel: MovieDetailViewModel by viewModels()
    lateinit var mViewBinding: MovieDetailFragmentBinding
    private val movieDetailArgument : MovieDetailFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         mViewBinding = MovieDetailFragmentBinding.inflate(inflater,container,false)
         return mViewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewBinding.movieTitle.text = movieDetailArgument.movieDetail.title?:""
        mViewBinding.movieDescription.text = movieDetailArgument.movieDetail.description?:""
        mViewBinding.itemImage.setImage(movieDetailArgument.movieDetail.posterPath)
    }


}