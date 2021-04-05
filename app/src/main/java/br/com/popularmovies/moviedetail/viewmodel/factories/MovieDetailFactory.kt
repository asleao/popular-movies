package br.com.popularmovies.moviedetail.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel
import br.com.popularmovies.repositories.movie.MovieRepository
import javax.inject.Inject

class MovieDetailFactory @Inject constructor(
        private val mMovieRepository: MovieRepository,
        private val movieId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(mMovieRepository, movieId) as T
    }
}