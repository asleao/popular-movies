package br.com.popularmovies.services.movieService.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MovieReviewDto(
        @Json(name = "author")
        val author: String = "",
        @Json(name = "content")
        val content: String = ""
) : Parcelable
