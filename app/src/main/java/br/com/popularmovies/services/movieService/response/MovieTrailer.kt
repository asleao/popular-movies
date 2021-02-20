package br.com.popularmovies.services.movieService.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class MovieTrailer(@Json(name = "id")
                        val id: String,
                        @Json(name = "iso_639_1")
                        val iso6391: String,
                        @Json(name = "iso_3166_1")
                        val iso31661: String,
                        @Json(name = "key")
                        val key: String,
                        @Json(name = "name")
                        val name: String,
                        @Json(name = "site")
                        val site: String,
                        @Json(name = "size")
                        val size: String,
                        @Json(name = "type")
                        val type: String) : Parcelable

