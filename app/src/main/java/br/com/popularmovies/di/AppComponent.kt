package br.com.popularmovies.di

import android.content.Context
import br.com.popularmovies.di.modules.NetworkModule
import br.com.popularmovies.di.subcomponents.MovieComponent
import br.com.popularmovies.di.subcomponents.MovieDetailComponent
import br.com.popularmovies.movies.ui.MovieActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [NetworkModule::class, AppSubcomponents::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun movieComponent(): MovieComponent.Factory
    fun movieDetailComponent(): MovieDetailComponent.Factory
    fun inject(movieActivity: MovieActivity)

}