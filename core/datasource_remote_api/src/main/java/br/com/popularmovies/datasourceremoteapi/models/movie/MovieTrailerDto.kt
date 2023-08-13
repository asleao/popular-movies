package br.com.popularmovies.datasourceremoteapi.models.movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@com.squareup.moshi.JsonClass(generateAdapter = true)
data class MovieTrailerDto(
    @com.squareup.moshi.Json(name = "id")
    val id: String,
    @com.squareup.moshi.Json(name = "iso_639_1")
    val iso6391: String,
    @com.squareup.moshi.Json(name = "iso_3166_1")
    val iso31661: String,
    @com.squareup.moshi.Json(name = "key")
    val key: String,
    @com.squareup.moshi.Json(name = "name")
    val name: String,
    @com.squareup.moshi.Json(name = "site")
    val site: String,
    @com.squareup.moshi.Json(name = "size")
    val size: String,
    @com.squareup.moshi.Json(name = "type")
    val type: String
)

