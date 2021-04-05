package br.com.popularmovies.di.subcomponents

import br.com.popularmovies.movies.ui.MovieFragment
import dagger.Subcomponent

@Subcomponent
interface MovieComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MovieComponent
    }

    fun inject(movieFragment: MovieFragment)
}