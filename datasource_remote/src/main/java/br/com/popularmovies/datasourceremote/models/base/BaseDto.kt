package br.com.popularmovies.datasourceremote.models.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class BaseDto<T>(@Json(name = "results") val results: List<T>)
