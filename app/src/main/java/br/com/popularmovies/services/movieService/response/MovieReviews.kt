package br.com.popularmovies.services.movieService.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieReviews(
    @field:Json(name = "results")
    val reviews: List<MovieReview>
) : Parcelable
