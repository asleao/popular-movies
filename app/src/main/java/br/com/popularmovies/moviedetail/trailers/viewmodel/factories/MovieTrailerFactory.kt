package br.com.popularmovies.moviedetail.trailers.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.popularmovies.moviedetail.trailers.viewmodel.MovieTrailerViewModel
import br.com.popularmovies.repositories.movie.MovieRepository
import javax.inject.Inject

class MovieTrailerFactory @Inject constructor(
        private val mMovieRepository: MovieRepository,
        private val movieId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieTrailerViewModel(mMovieRepository, movieId) as T
    }
}