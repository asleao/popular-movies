package br.com.popularmovies.services.movieService.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import br.com.popularmovies.data.model.OldResource
import br.com.popularmovies.movies.Constants.FILTER_FAVORITES
import br.com.popularmovies.services.movieService.response.Movie
import br.com.popularmovies.services.movieService.response.MovieReviews
import br.com.popularmovies.services.movieService.response.MovieTrailers
import br.com.popularmovies.services.movieService.response.Movies

class MovieRepository private constructor(
    private val mMovieLocalDataSource: MovieDataSource,
    private val mMovieRemoteDataSource: MovieDataSource
) : MovieDataSource {

    override fun getMovies(orderBy: String): LiveData<OldResource<Movies>> {
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

    override fun getMovieReviews(movieId: Int): LiveData<OldResource<MovieReviews>> {
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

    override fun getMovieTrailers(movieId: Int): LiveData<OldResource<MovieTrailers>> {
        return mMovieRemoteDataSource.getMovieTrailers(movieId)
    }

    companion object {

        @Volatile
        private var INSTANCE: MovieRepository? = null

        fun getInstance(
            mMovieLocalDataSource: MovieDataSource,
            mMovieRemoteDataSource: MovieDataSource
        ): MovieRepository? {
            if (INSTANCE == null) {
                synchronized(MovieRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MovieRepository(mMovieLocalDataSource, mMovieRemoteDataSource)
                    }
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
