package br.com.popularmovies.moviedetail.reviews.viewModel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import br.com.popularmovies.moviedetail.reviews.viewModel.MovieReviewViewModel
import br.com.popularmovies.services.movieService.MovieRepository

class MovieReviewFactory(private val mMovieRepository: MovieRepository, private val movieId: Int) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieReviewViewModel(mMovieRepository, this.movieId) as T
    }
}
