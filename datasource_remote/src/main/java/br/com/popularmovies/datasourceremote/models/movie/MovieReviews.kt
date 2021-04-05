package br.com.popularmovies.datasourceremote.models.movie

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MovieReviews(@Json(name = "results") val reviewDtos: List<MovieReviewDto>) : Parcelable
