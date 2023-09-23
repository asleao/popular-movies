package br.com.popularmovies.domain.api

import br.com.popularmovies.domain.api.usecases.GetMovieFavoriteUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieReviewsUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieTrailersUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieUseCase
import br.com.popularmovies.domain.api.usecases.GetMoviesUseCase
import br.com.popularmovies.domain.api.usecases.GetRandomNowPlayingMovieUseCase
import br.com.popularmovies.domain.api.usecases.SearchMoviesUseCase
import br.com.popularmovies.domain.api.usecases.UpdateMovieFavoriteUseCase

interface DomainComponentProvider {
    val getMovieUseCase: GetMovieUseCase
    val getMoviesUseCase: GetMoviesUseCase
    val searchMoviesUseCase: SearchMoviesUseCase
    val getRandomNowPlayingMovieUseCase: GetRandomNowPlayingMovieUseCase
    val updateMovieFavoriteUseCase: UpdateMovieFavoriteUseCase
    val getMovieReviewsUseCase: GetMovieReviewsUseCase
    val getMovieTrailersUseCase: GetMovieTrailersUseCase
    val getMovieFavoriteUseCase: GetMovieFavoriteUseCase
}