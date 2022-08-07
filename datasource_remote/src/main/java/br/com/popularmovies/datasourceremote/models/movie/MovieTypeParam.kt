package br.com.popularmovies.datasourceremote.models.movie

enum class MovieTypeParam(val path: String) {
    TopRated("top_rated"),
    MostPopular("popular"),
    NowPlaying("now_playing"),
    Unknown("unknown"),
}