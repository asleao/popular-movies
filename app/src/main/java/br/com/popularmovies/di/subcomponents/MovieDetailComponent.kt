package br.com.popularmovies.di.subcomponents

import br.com.popularmovies.moviedetail.reviews.ui.MovieReviewFragment
import br.com.popularmovies.moviedetail.trailers.ui.MovieTrailerFragment
import br.com.popularmovies.moviedetail.ui.MovieDetailFragment
import dagger.Subcomponent

@Subcomponent
interface MovieDetailComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MovieDetailComponent
    }

    fun inject(movieDetailFragment: MovieDetailFragment)
    fun inject(movieReviewFragment: MovieReviewFragment)
    fun inject(movieTrailerFragment: MovieTrailerFragment)
}