package br.com.popularmovies.domain.api

import br.com.popularmovies.domain.api.usecases.GetMovieReviewsUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieTrailersUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieUseCase
import br.com.popularmovies.domain.api.usecases.GetMoviesUseCase
import br.com.popularmovies.domain.api.usecases.GetRandomNowPlayingMovieUseCase
import br.com.popularmovies.domain.api.usecases.SaveMovieToFavoritesUseCase

interface DomainComponentProvider {
    val getMovieUseCase: GetMovieUseCase
    val getMoviesUseCase: GetMoviesUseCase
    val getRandomNowPlayingMovieUseCase: GetRandomNowPlayingMovieUseCase
    val saveMovieToFavoritesUseCase: SaveMovieToFavoritesUseCase
    val getMovieReviewsUseCase: GetMovieReviewsUseCase
    val getMovieTrailersUseCase: GetMovieTrailersUseCase
}