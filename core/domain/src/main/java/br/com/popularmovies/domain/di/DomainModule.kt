package br.com.popularmovies.domain.di

import br.com.popularmovies.domain.api.usecases.GetMovieReviewsUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieTrailersUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieUseCase
import br.com.popularmovies.domain.api.usecases.GetMoviesUseCase
import br.com.popularmovies.domain.api.usecases.GetRandomNowPlayingMovieUseCase
import br.com.popularmovies.domain.api.usecases.SaveMovieToFavoritesUseCase
import br.com.popularmovies.domain.usecases.movies.GetMovieUseCaseImpl
import br.com.popularmovies.domain.usecases.movies.GetMoviesUseCaseImpl
import br.com.popularmovies.domain.usecases.movies.GetRandomNowPlayingMovieUseCaseImpl
import br.com.popularmovies.domain.usecases.movies.favorites.SaveMovieToFavoritesUseCaseImpl
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
    fun bindsSaveMovieToFavoritesUseCase(saveMovieToFavoritesUseCase: SaveMovieToFavoritesUseCaseImpl): SaveMovieToFavoritesUseCase

    @Binds
    fun bindsGetMovieReviewsUseCase(getMovieReviewsUseCase: GetMovieReviewsUseCaseImpl): GetMovieReviewsUseCase

    @Binds
    fun bindsGetMovieTrailersUseCase(getMovieTrailersUseCase: GetMovieTrailersUseCaseImpl): GetMovieTrailersUseCase
}