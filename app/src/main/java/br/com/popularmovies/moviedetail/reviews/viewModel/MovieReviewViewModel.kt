package br.com.popularmovies.moviedetail.reviews.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.popularmovies.core.network.retrofit.model.Error
import br.com.popularmovies.services.movieService.response.MovieReviews
import br.com.popularmovies.services.movieService.source.MovieRepository
import br.com.popularmovies.utils.validateResponse
import kotlinx.coroutines.launch

class MovieReviewViewModel(mMovieRepository: MovieRepository, movieId: Int) : ViewModel() {
    private val _error = MutableLiveData<Error>()
    val error: LiveData<Error>
        get() = _error
    val _reviews = MutableLiveData<MovieReviews>()
    val reviews: LiveData<MovieReviews>
        get() = _reviews

    private val movieId = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            val resource = mMovieRepository.getMovieReviews(movieId)
            resource.validateResponse(_reviews, _error)
        }
    }

    fun tryAgain() {
        movieId.value = movieId.value
    }
}
