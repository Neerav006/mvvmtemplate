package com.example.mvvm.ui.movies

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.mvvm.R
import com.example.mvvm.utils.AppConstants

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("setImage")
    fun ImageView.setImage(relativeImageUrl: String?) {
        val requestOptions = RequestOptions().priority(Priority.IMMEDIATE)
            .placeholder(R.mipmap.ic_launcher)

        Glide.with(context).load(AppConstants.IMAGE_BASE_URL + relativeImageUrl)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(this)
    }


}