package  br.com.popularmovies.moviedetails.di

import br.com.popularmovies.model.feature.FeatureApi
import br.com.popularmovies.moviedetails.MovieDetailsFeatureApiImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MovieDetailsApiModule {

    @Binds
    abstract fun bindMovieDetailsFeatureApi(movieDetailsFeatureApi: MovieDetailsFeatureApiImpl): FeatureApi
}