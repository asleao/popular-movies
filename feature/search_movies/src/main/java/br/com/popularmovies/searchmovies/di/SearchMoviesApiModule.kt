package  br.com.popularmovies.searchmovies.di

import br.com.popularmovies.searchmovies.SearchMoviesFeatureApiImpl
import br.com.popularmovies.searchmovies.api.SearchMoviesFeatureApi
import dagger.Binds
import dagger.Module

@Module
abstract class SearchMoviesApiModule {

    @Binds
    abstract fun bindSearchMoviesFeatureApi(searchMoviesFeatureApi: SearchMoviesFeatureApiImpl): SearchMoviesFeatureApi
}