package br.com.popularmovies.datanetwork.models.movie

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MovieTrailers(@Json(name = "results") val trailerDtos: List<MovieTrailerDto>) : Parcelable