package br.com.popularmovies.datasourceremoteapi.models.movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@com.squareup.moshi.JsonClass(generateAdapter = true)
data class MovieReviewDto(
    @com.squareup.moshi.Json(name = "author")
    val author: String = "",
    @com.squareup.moshi.Json(name = "content")
    val content: String = ""
)
