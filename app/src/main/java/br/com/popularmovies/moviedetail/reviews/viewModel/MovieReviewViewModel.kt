package br.com.popularmovies.moviedetail.reviews.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.com.popularmovies.data.model.Resource
import br.com.popularmovies.services.movieService.response.MovieReviews
import br.com.popularmovies.services.movieService.source.MovieRepository

class MovieReviewViewModel(mMovieRepository: MovieRepository, movieId: Int) : ViewModel() {
    val reviews: LiveData<Resource<MovieReviews>>
    private val movieId = MutableLiveData<Int>()

    init {
        reviews = Transformations.switchMap(this.movieId) { input ->
            if (input != null) mMovieRepository.getMovieReviews(input) else null
        }
        this.movieId.value = movieId
    }

    fun tryAgain() {
        movieId.value = movieId.value
    }
}
