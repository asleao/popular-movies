package br.com.popularmovies.di

import br.com.popularmovies.MovieActivity
import br.com.popularmovies.common.di.CommonProvider
import br.com.popularmovies.core.api.DatabaseComponentProvider
import br.com.popularmovies.data.di.DataComponentProvider
import br.com.popularmovies.datasourceremoteapi.NetworkComponentProvider
import br.com.popularmovies.di.subcomponents.MovieDetailComponent
import br.com.popularmovies.domain.di.DomainComponentProvider
import br.com.popularmovies.home.api.HomeFeatureProvider
import br.com.popularmovies.moviedetail.reviews.viewModel.MovieReviewViewModel
import br.com.popularmovies.moviedetail.trailers.viewmodel.MovieTrailerViewModel
import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [
        CommonProvider::class,
        DomainComponentProvider::class,
        DataComponentProvider::class,
        DatabaseComponentProvider::class,
        NetworkComponentProvider::class,
        HomeFeatureProvider::class
    ],
    modules = [
        AppSubcomponents::class
    ]
)
@Singleton
interface AppComponent : AppProvider {

    val movieDetailViewModelFactory: MovieDetailViewModel.Factory
    val movieReviewViewModelFactory: MovieReviewViewModel.Factory
    val movieTrailerViewModelFactory: MovieTrailerViewModel.Factory

    fun movieDetailComponent(): MovieDetailComponent.Factory
    fun inject(movieActivity: MovieActivity)
}