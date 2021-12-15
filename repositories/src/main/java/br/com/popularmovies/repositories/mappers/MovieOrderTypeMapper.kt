package br.com.popularmovies.repositories.mappers

import br.com.popularmovies.entities.movie.MovieOrderType

fun MovieOrderType.toRequest(): String {
    return when (this) {
        MovieOrderType.Favorites -> "favorites"
        MovieOrderType.MostPopular -> "popular"
        MovieOrderType.TopHated -> "top_rated"
    }
}