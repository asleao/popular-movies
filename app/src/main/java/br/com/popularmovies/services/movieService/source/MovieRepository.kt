package br.com.popularmovies.services.movieService.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import br.com.popularmovies.core.network.retrofit.model.Resource
import br.com.popularmovies.data.model.OldResource
import br.com.popularmovies.di.qualifiers.MoviesLocalDataSource
import br.com.popularmovies.di.qualifiers.MoviesRemoteDataSource
import br.com.popularmovies.movies.Constants.FILTER_FAVORITES
import br.com.popularmovies.services.movieService.response.Movie
import br.com.popularmovies.services.movieService.response.MovieReviews
import br.com.popularmovies.services.movieService.response.MovieTrailers
import br.com.popularmovies.services.movieService.response.Movies
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
        @MoviesLocalDataSource private val mMovieLocalDataSource: MovieDataSource,
        @MoviesRemoteDataSource private val mMovieRemoteDataSource: MovieDataSource
) : MovieDataSource {

    override suspend fun getMovies(orderBy: String): Resource<Movies> {
        return if (orderBy == FILTER_FAVORITES) {
            mMovieLocalDataSource.getMovies(orderBy)
        } else {
            mMovieRemoteDataSource.getMovies(orderBy)
        }
    }

    override fun getMovie(movieId: Int): LiveData<OldResource<Movie>> {
        val movie = MediatorLiveData<OldResource<Movie>>()
        val dbSource = mMovieLocalDataSource.getMovie(movieId)
        val networkSource = mMovieRemoteDataSource.getMovie(movieId)
        movie.addSource(dbSource) { resource ->
            when (resource.status) {
                OldResource.Status.SUCCESS -> if (resource.data == null) {
                    movie.removeSource(dbSource)
                    movie.addSource(networkSource) { resource -> movie.postValue(resource) }
                } else {
                    movie.postValue(resource)
                }
                OldResource.Status.ERROR -> movie.postValue(resource)
            }
        }
        return movie
    }

    override suspend fun getMovieReviews(movieId: Int): Resource<MovieReviews> {
        return mMovieRemoteDataSource.getMovieReviews(movieId)
    }

    override fun saveToFavorites(movieId: Int, status: Boolean): LiveData<OldResource<Boolean>> {
        return mMovieLocalDataSource.saveToFavorites(movieId, status)
    }

    override fun saveMovies(movies: List<Movie>): LiveData<OldResource<Void>> {
        return mMovieLocalDataSource.saveMovies(movies)
    }

    override fun saveMovie(movie: Movie): LiveData<OldResource<Void>> {
        return mMovieLocalDataSource.saveMovie(movie)
    }

    override fun removeMovie(movie: Movie): LiveData<OldResource<Void>> {
        return mMovieLocalDataSource.removeMovie(movie)
    }

    override suspend fun getMovieTrailers(movieId: Int): Resource<MovieTrailers> {
        return mMovieRemoteDataSource.getMovieTrailers(movieId)
    }
}
