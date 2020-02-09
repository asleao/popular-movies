package br.com.popularmovies.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MoviesLocalDataSource


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MoviesRemoteDataSource
