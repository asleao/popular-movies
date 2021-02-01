package br.com.popularmovies.moviedetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.popularmovies.core.network.retrofit.model.Error
import br.com.popularmovies.services.movieService.response.Movie
import br.com.popularmovies.services.movieService.source.MovieRepository
import br.com.popularmovies.utils.validateResponse
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

    private val _isMovieFavorite = MutableLiveData<Boolean>()
    val isMovieFavorite: LiveData<Boolean>
        get() = _isMovieFavorite

    init {
        getMovie()
    }

    private fun getMovie() {
        viewModelScope.launch {
            showLoading(true)
            val resource = mMovieRepository.getMovie(movieId)
            resource.validateResponse(_movie, _error)
        }
    }

    fun updateMovie() {
        movie.value?.let { movie ->
            viewModelScope.launch {
                //TODO Refactor
                val resource = mMovieRepository.saveToFavorites(movie.copy(isFavorite = !movie.isFavorite))
                resource.validateResponse(_isMovieFavorite, _error)
                _isMovieFavorite.value?.let {
                    _movie.value = movie.copy(isFavorite = it)
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