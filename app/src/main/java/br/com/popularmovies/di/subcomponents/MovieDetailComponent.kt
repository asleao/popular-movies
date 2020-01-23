package br.com.popularmovies.di.subcomponents

import br.com.popularmovies.moviedetail.ui.MovieDetailFragment
import dagger.Subcomponent

@Subcomponent
interface MovieDetailComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MovieDetailComponent
    }

    fun inject(movieDetailFragment: MovieDetailFragment)
}