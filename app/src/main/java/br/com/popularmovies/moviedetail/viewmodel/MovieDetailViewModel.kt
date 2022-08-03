package br.com.popularmovies.moviedetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.popularmovies.common.models.base.NetworkError
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieReview
import br.com.popularmovies.entities.movie.MovieTrailer
import br.com.popularmovies.usecases.movies.GetMovieUseCase
import br.com.popularmovies.usecases.movies.favorites.SaveMovieToFavoritesUseCase
import br.com.popularmovies.usecases.movies.reviews.GetMovieReviewsUseCase
import br.com.popularmovies.usecases.movies.trailers.GetMovieTrailersUseCase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class MovieDetailViewModel @AssistedInject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val getMovieTrailersUseCase: GetMovieTrailersUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val saveMovieToFavoritesUseCase: SaveMovieToFavoritesUseCase,
    @Assisted private val movieArg: Movie
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(movie: Movie): MovieDetailViewModel
    }

    val loading = MutableLiveData<Boolean>()

    private val _error = MutableLiveData<NetworkError>()
    val error: LiveData<NetworkError>
        get() = _error

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    private val _trailers = MutableLiveData<List<MovieTrailer>>()
    val trailers: LiveData<List<MovieTrailer>> = _trailers

    private val _reviews = MutableLiveData<List<MovieReview>>()
    val reviews: LiveData<List<MovieReview>> = _reviews

    private val _isMovieFavorite = MutableLiveData<Boolean>()
    val isMovieFavorite: LiveData<Boolean>
        get() = _isMovieFavorite

    private val _playTrailer = MutableLiveData<String>()
    val playTrailer: LiveData<String>
        get() = _playTrailer

    init {
        getMovie()
        getTrailers()
        getReviews()
    }

    private fun getMovie() {
        viewModelScope.launch {
            showLoading(true)
            val params = GetMovieUseCase.Params(movieArg.id)
            when (val result = getMovieUseCase.build(params)) {
                is Result.Success -> {
                    _movie.value = result.data
                }
                is Result.Error -> _error.value = result.error
            }
        }
    }

    private fun getTrailers() {
        viewModelScope.launch {
            showLoading(true)
            val params = GetMovieTrailersUseCase.Params(movieArg.id)
            when (val result = getMovieTrailersUseCase.build(params)) {
                is Result.Success -> _trailers.value = result.data
                is Result.Error -> _error.value = result.error
            }
        }
    }

    fun getReviews() {
        viewModelScope.launch {
            showLoading(true)
            val params = GetMovieReviewsUseCase.Params(movieArg.id)
            when (val result = getMovieReviewsUseCase.build(params)) {
                is Result.Success -> _reviews.value = result.data
                is Result.Error -> _error.value = result.error
            }
        }
    }

    fun updateMovie() {
        movie.value?.let { movie ->
            viewModelScope.launch {
                //TODO Refactor
                val params =
                    SaveMovieToFavoritesUseCase.Params(movie.copy(isFavorite = !movie.isFavorite))
                when (val result =
                    saveMovieToFavoritesUseCase.build(params)) {
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

    fun playTrailer(key: String) {
        _playTrailer.value = key
    }
}