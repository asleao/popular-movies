package br.com.popularmovies.entities.movie

data class MovieTrailer(
    val id: String,
    val iso6391: String,
    val iso31661: String,
    val key: String,
    val name: String,
    val site: String,
    val size: String,
    val type: String
)

