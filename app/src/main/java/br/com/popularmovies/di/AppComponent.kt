package br.com.popularmovies.di

import android.content.Context
import br.com.popularmovies.MovieActivity
import br.com.popularmovies.data.di.DataComponent
import br.com.popularmovies.datasourcedb.di.DatabaseComponent
import br.com.popularmovies.datasourceremote.di.NetworkComponent
import br.com.popularmovies.di.modules.ViewModelModule
import br.com.popularmovies.di.subcomponents.MovieDetailComponent
import br.com.popularmovies.domain.di.DomainComponent
import br.com.popularmovies.moviedetail.reviews.viewModel.MovieReviewViewModel
import br.com.popularmovies.moviedetail.trailers.viewmodel.MovieTrailerViewModel
import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [
        DomainComponent::class,
        DataComponent::class,
        DatabaseComponent::class,
        NetworkComponent::class
    ],
    modules = [
        AppSubcomponents::class,
        ViewModelModule::class
    ]
)
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            dataComponent: DataComponent,
            databaseComponent: DatabaseComponent,
            networkComponent: NetworkComponent,
            domainComponent: DomainComponent
        ): AppComponent
    }

    val movieDetailViewModelFactory: MovieDetailViewModel.Factory
    val movieReviewViewModelFactory: MovieReviewViewModel.Factory
    val movieTrailerViewModelFactory: MovieTrailerViewModel.Factory

    //    fun movieComponent(): br.com.popularmovies.home.di.HomeComponent.Factory
    fun movieDetailComponent(): MovieDetailComponent.Factory
    fun inject(movieActivity: MovieActivity)

}