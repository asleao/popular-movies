package br.com.popularmovies.services.movieService.source

import br.com.popularmovies.core.network.retrofit.model.Resource
import br.com.popularmovies.movies.Constants.FILTER_FAVORITES
import br.com.popularmovies.services.movieService.response.Movie
import br.com.popularmovies.services.movieService.response.MovieReviews
import br.com.popularmovies.services.movieService.response.MovieTrailers
import br.com.popularmovies.services.movieService.response.Movies
import br.com.popularmovies.services.movieService.source.local.MovieLocalDataSource
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
        private val mMovieLocalDataSource: MovieLocalDataSource,
        private val mMovieRemoteDataSource: MovieRemoteDataSource
) : MovieDataSource {

    override suspend fun getMovies(orderBy: String): Resource<Movies> {
        return if (orderBy == FILTER_FAVORITES) {
            mMovieLocalDataSource.getFavoriteMovies(true) //TODO Extract to a new method
        } else {
            mMovieRemoteDataSource.getMovies(orderBy)
        }
    }

    override suspend fun getMovie(movieId: Int): Resource<Movie> {
        val dbSource = mMovieLocalDataSource.getMovie(movieId)
        val networkSource = mMovieRemoteDataSource.getMovie(movieId)
        return if (dbSource.data == null) {
            networkSource
        } else {
            dbSource
        }
    }

    override suspend fun getMovieReviews(movieId: Int): Resource<MovieReviews> {
        return mMovieRemoteDataSource.getMovieReviews(movieId)
    }

    override suspend fun saveToFavorites(movie: Movie): Resource<Unit> {
        val isMovieExists = mMovieLocalDataSource.isMovieExists(movie.id)
        return if (isMovieExists.data == true) {
            mMovieLocalDataSource.saveToFavorites(movie)
        } else {
            mMovieLocalDataSource.insertMovie(movie)
        }
    }

    override suspend fun getMovieTrailers(movieId: Int): Resource<MovieTrailers> {
        return mMovieRemoteDataSource.getMovieTrailers(movieId)
    }
}
