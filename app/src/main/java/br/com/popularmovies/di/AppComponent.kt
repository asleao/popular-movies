package br.com.popularmovies.di

import android.content.Context
import br.com.popularmovies.movies.ui.MovieActivity
import br.com.popularmovies.movies.ui.MovieFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(movieActivity: MovieActivity)
    fun inject(movieFragment: MovieFragment)
}