package br.com.popularmovies.di

import android.content.Context
import br.com.popularmovies.datanetwork.di.NetworkModule
import br.com.popularmovies.di.modules.AssistedInjectModule
import br.com.popularmovies.di.modules.DatabaseModule
import br.com.popularmovies.di.modules.ViewModelModule
import br.com.popularmovies.di.subcomponents.MovieComponent
import br.com.popularmovies.di.subcomponents.MovieDetailComponent
import br.com.popularmovies.moviedetail.reviews.viewModel.MovieReviewViewModel
import br.com.popularmovies.moviedetail.trailers.viewmodel.MovieTrailerViewModel
import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel
import br.com.popularmovies.movies.ui.MovieActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    DatabaseModule::class,
    AppSubcomponents::class,
    ViewModelModule::class,
    AssistedInjectModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    val movieDetailViewModelFactory: MovieDetailViewModel.Factory
    val movieReviewViewModelFactory: MovieReviewViewModel.Factory
    val movieTrailerViewModelFactory: MovieTrailerViewModel.Factory

    fun movieComponent(): MovieComponent.Factory
    fun movieDetailComponent(): MovieDetailComponent.Factory
    fun inject(movieActivity: MovieActivity)

}