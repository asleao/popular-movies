package br.com.popularmovies.core.api

import androidx.paging.PagingSource
import br.com.popularmovies.core.api.models.movie.MovieTable
import br.com.popularmovies.core.api.models.movie.MovieTypeTable
import br.com.popularmovies.core.api.models.review.ReviewTable
import br.com.popularmovies.core.api.models.trailer.TrailerTable
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    fun getMoviesPagingSourceFactory(type: MovieTypeTable): PagingSource<Int, MovieTable>
    fun getMovies(type: MovieTypeTable): Flow<List<MovieTable>>
    suspend fun insertAllMovies(movies: List<MovieTable>)
    suspend fun deleteAllMovies(type: MovieTypeTable)
    suspend fun getMovie(movieId: Long): MovieTable
    suspend fun insertMovie(movie: MovieTable)
    suspend fun deleteMovie(movieId: Long)
    suspend fun getMovieReviews(movieId: Long): List<ReviewTable>
    suspend fun insertMovieReviews(reviews: List<ReviewTable>)
    suspend fun deleteMovieReviews(movieId: Long)
    suspend fun getMovieTrailers(movieId: Long): List<TrailerTable>
    suspend fun insertMovieTrailers(reviews: List<TrailerTable>)
    suspend fun deleteMovieTrailers(movieId: Long)
}
