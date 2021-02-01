package br.com.popularmovies.services.movieService.source

import androidx.lifecycle.LiveData
import br.com.popularmovies.core.network.retrofit.model.Resource
import br.com.popularmovies.data.model.OldResource
import br.com.popularmovies.services.movieService.response.Movie
import br.com.popularmovies.services.movieService.response.MovieReviews
import br.com.popularmovies.services.movieService.response.MovieTrailers
import br.com.popularmovies.services.movieService.response.Movies

interface MovieDataSource {

    suspend fun getMovies(orderBy: String): Resource<Movies>

    suspend fun getMovie(movieId: Int): Resource<Movie>

    suspend fun getMovieReviews(movieId: Int): Resource<MovieReviews>

    suspend fun saveToFavorites(movie: Movie): Resource<Boolean>

    fun insertMovies(movies: List<Movie>): LiveData<OldResource<Void>>

    suspend fun getMovieTrailers(movieId: Int): Resource<MovieTrailers>
}
