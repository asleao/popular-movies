package br.com.popularmovies.di

import br.com.popularmovies.MovieActivity
import br.com.popularmovies.common.di.CommonProvider
import br.com.popularmovies.core.api.DatabaseComponentProvider
import br.com.popularmovies.core.data.api.DataComponentProvider
import br.com.popularmovies.datasourceremoteapi.NetworkComponentProvider
import br.com.popularmovies.domain.api.DomainComponentProvider
import br.com.popularmovies.home.api.HomeFeatureProvider
import br.com.popularmovies.home.di.HomeFragmentModule
import br.com.popularmovies.home.di.HomeViewModelModule
import br.com.popularmovies.movie.details.di.MovieDetailsFeatureProvider
import br.com.popularmovies.movie.details.di.MovieDetailsFragmentModule
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [
        CommonProvider::class,
        DomainComponentProvider::class,
        DataComponentProvider::class,
        DatabaseComponentProvider::class,
        NetworkComponentProvider::class,
        HomeFeatureProvider::class,
        MovieDetailsFeatureProvider::class
    ],
    modules = [
        /*
        * TODO
        *  FragmentFactory and ViewModelFactory only generate map providers when there
        *  is at least one bind of fragment or viewmodel. So factory instance is only created
        *  in those circumstances. Still need to check if there's a way around this problem and
        *  if it's going to work when there's other feature modules.
        * */
        HomeFragmentModule::class,
        HomeViewModelModule::class,
        MovieDetailsFragmentModule::class,
    ]
)
@Singleton
interface AppComponent : AppProvider {

    fun inject(movieActivity: MovieActivity)
}