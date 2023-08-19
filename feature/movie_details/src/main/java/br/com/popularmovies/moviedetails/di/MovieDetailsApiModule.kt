package  br.com.popularmovies.moviedetails.di

import br.com.popularmovies.feature.moviedetails.api.MovieDetailsFeatureApi
import br.com.popularmovies.moviedetails.MovieDetailsFeatureApiImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MovieDetailsApiModule {

    @Binds
    abstract fun bindMovieDetailsFeatureApi(movieDetailsFeatureApi: MovieDetailsFeatureApiImpl): MovieDetailsFeatureApi
}