package br.com.popularmovies.moviedetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.popularmovies.common.models.base.Error
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieTrailer
import br.com.popularmovies.repositories.movie.MovieRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class MovieDetailViewModel @AssistedInject constructor(
    private val mMovieRepository: MovieRepository,
    @Assisted private val movieId: Int
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(movieId: Int): MovieDetailViewModel
    }

    val loading = MutableLiveData<Boolean>()

    private val _error = MutableLiveData<Error>()
    val error: LiveData<Error>
        get() = _error

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    private val _trailers = MutableLiveData<List<MovieTrailer>>()
    val trailers: LiveData<List<MovieTrailer>> = _trailers

    private val _isMovieFavorite = MutableLiveData<Boolean>()
    val isMovieFavorite: LiveData<Boolean>
        get() = _isMovieFavorite

    init {
        getMovie()
        getTrailers()
    }

    private fun getMovie() {
        viewModelScope.launch {
            showLoading(true)
            when (val result = mMovieRepository.getMovie(movieId)) {
                is Result.Success -> _movie.value = result.data
                is Result.Error -> _error.value = result.error
            }
        }
    }

    private fun getTrailers() {
        viewModelScope.launch {
            showLoading(true)
            when (val result = mMovieRepository.getMovieTrailers(movieId)) {
                is Result.Success -> _trailers.value = result.data
                is Result.Error -> _error.value = result.error
            }
        }
    }

    fun updateMovie() {
        movie.value?.let { movie ->
            viewModelScope.launch {
                //TODO Refactor
                when (val result =
                    mMovieRepository.saveToFavorites(movie.copy(isFavorite = !movie.isFavorite))) {
                    is Result.Success -> {
                        _isMovieFavorite.value = !movie.isFavorite
                        _movie.value = movie.copy(isFavorite = !movie.isFavorite)
                    }
                    is Result.Error -> {
                        _error.value = result.error
                    }
                }
            }
        }
    }

    fun showLoading(value: Boolean) {
        loading.value = value
    }

    fun tryAgain() {
        getMovie()
    }

}