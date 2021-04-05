package br.com.popularmovies.movies.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.popularmovies.movies.viewmodel.MovieViewModel
import br.com.popularmovies.repositories.movie.MovieRepository
import javax.inject.Inject

class MovieFactory @Inject constructor(
        private val mMovieRepository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(mMovieRepository) as T
    }
}