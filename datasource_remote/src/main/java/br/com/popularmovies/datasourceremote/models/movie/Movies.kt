package br.com.popularmovies.datasourceremote.models.movie

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Movies(@Json(name = "results") val movieDtos: List<MovieDto>) : Parcelable