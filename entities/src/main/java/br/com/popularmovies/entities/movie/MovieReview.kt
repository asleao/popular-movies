package br.com.popularmovies.entities.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieReview(
        val author: String,
        val content: String
) : Parcelable
