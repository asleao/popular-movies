package br.com.popularmovies.domain.di

import br.com.popularmovies.domain.usecases.movies.GetMovieUseCase
import br.com.popularmovies.domain.usecases.movies.GetMoviesUseCase
import br.com.popularmovies.domain.usecases.movies.GetRandomNowPlayingMovieUseCase

interface DomainComponentProvider {
    val getMovieUseCase: GetMovieUseCase
    val getMoviesUseCase: GetMoviesUseCase
    val getRandomNowPlayingMovieUseCase: GetRandomNowPlayingMovieUseCase
}