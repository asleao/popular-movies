package br.com.popularmovies.core.data.api


interface DataComponentProvider {
    val movieRepository: MovieRepository
}