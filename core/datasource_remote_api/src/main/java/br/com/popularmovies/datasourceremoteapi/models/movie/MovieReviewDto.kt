package br.com.popularmovies.datasourceremoteapi.models.movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieReviewDto(
    @Json(name = "author")
    val author: String = "",
    @Json(name = "content")
    val content: String = ""
)
