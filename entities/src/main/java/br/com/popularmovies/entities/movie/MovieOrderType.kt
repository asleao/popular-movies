package br.com.popularmovies.entities.movie

sealed class MovieOrderType {
    object Favorites : MovieOrderType()
    object TopHated : MovieOrderType()
    object MostPopular : MovieOrderType()
}