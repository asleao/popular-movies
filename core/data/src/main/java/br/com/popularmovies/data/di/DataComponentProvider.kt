package br.com.popularmovies.data.di

import br.com.popularmovies.data.movie.MovieRepository

interface DataComponentProvider {
    val movieRepository: MovieRepository
}