package com.example.mvvm.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MoviesResponse(
    @SerializedName("total_pages") val total: Int = 0,
    val page: Int = 0,
    val results: List<Movie>
) {
    @Parcelize
    data class Movie(
        val voteCount: Int,
        val posterPath: String,
        val id: Long,
        val title: String,
        val description: String,
        val popularity: Double,
        val overview: String
    ):Parcelable
}