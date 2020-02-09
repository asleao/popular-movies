package br.com.popularmovies.moviedetail.viewmodel

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.com.popularmovies.data.model.OldResource
import br.com.popularmovies.services.movieService.response.Movie
import br.com.popularmovies.services.movieService.source.MovieRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class MovieDetailViewModel @AssistedInject constructor(private val mMovieRepository: MovieRepository, @Assisted private val movieId: Int) : ViewModel() {
    val favorites: LiveData<OldResource<Void>>
    private val mMovie: LiveData<OldResource<Movie>>
    private val movieStatus = MutableLiveData<Boolean>()
    private val _movieId = MutableLiveData<Int>()
    var movie: Movie? = null

    fun getmMovie(): LiveData<OldResource<Movie>> {
        return mMovie
    }

    fun saveFavorites(status: Boolean) {
        movieStatus.value = status
    }

    fun tryAgain() {
        _movieId.value = _movieId.value
    }

    init {
        mMovie = Transformations.switchMap(_movieId) { mMovieRepository.getMovie(movieId) }
        _movieId.value = movieId
        favorites = Transformations.switchMap(movieStatus, Function { isFavorite ->
            if (isFavorite != null) {
                movie?.isFavorite = isFavorite
                return@Function if (isFavorite) {
                    mMovieRepository.saveMovie(movie!!)
                } else {
                    mMovieRepository.removeMovie(movie!!)
                }
            }
            null
        })
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(movieId: Int): MovieDetailViewModel
    }
}