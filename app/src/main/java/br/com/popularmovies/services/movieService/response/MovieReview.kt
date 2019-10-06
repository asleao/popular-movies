package br.com.popularmovies.services.movieService.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieReview(
    @field:Json(name = "author")
    val author: String = "",
    @field:Json(name = "content")
    val content: String = ""
) : Parcelable
