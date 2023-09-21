package br.com.popularmovies.domain.di

import br.com.popularmovies.domain.api.usecases.GetMovieFavoriteUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieReviewsUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieTrailersUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieUseCase
import br.com.popularmovies.domain.api.usecases.GetMoviesUseCase
import br.com.popularmovies.domain.api.usecases.GetRandomNowPlayingMovieUseCase
import br.com.popularmovies.domain.api.usecases.UpdateMovieFavoriteUseCase
import br.com.popularmovies.domain.usecases.movies.GetMovieFavoriteUseCaseImpl
import br.com.popularmovies.domain.usecases.movies.GetMovieUseCaseImpl
import br.com.popularmovies.domain.usecases.movies.GetMoviesUseCaseImpl
import br.com.popularmovies.domain.usecases.movies.GetRandomNowPlayingMovieUseCaseImpl
import br.com.popularmovies.domain.usecases.movies.favorites.UpdateMovieFavoriteUseCaseImpl
import br.com.popularmovies.domain.usecases.movies.reviews.GetMovieReviewsUseCaseImpl
import br.com.popularmovies.domain.usecases.movies.trailers.GetMovieTrailersUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @Binds
    fun bindsGetMovieUseCase(getMovieUseCase: GetMovieUseCaseImpl): GetMovieUseCase

    @Binds
    fun bindsGetMoviesUseCase(getMoviesUseCase: GetMoviesUseCaseImpl): GetMoviesUseCase

    @Binds
    fun bindsGetRandomNowPlayingMovieUseCase(getRandomNowPlayingMovieUseCase: GetRandomNowPlayingMovieUseCaseImpl): GetRandomNowPlayingMovieUseCase

    @Binds
    fun bindsSaveMovieToFavoritesUseCase(saveMovieToFavoritesUseCase: UpdateMovieFavoriteUseCaseImpl): UpdateMovieFavoriteUseCase

    @Binds
    fun bindsGetMovieReviewsUseCase(getMovieReviewsUseCase: GetMovieReviewsUseCaseImpl): GetMovieReviewsUseCase

    @Binds
    fun bindsGetMovieTrailersUseCase(getMovieTrailersUseCase: GetMovieTrailersUseCaseImpl): GetMovieTrailersUseCase

    @Binds
    fun bindsGetMovieFavoriteUseCase(getMovieFavoriteUseCase: GetMovieFavoriteUseCaseImpl): GetMovieFavoriteUseCase
}