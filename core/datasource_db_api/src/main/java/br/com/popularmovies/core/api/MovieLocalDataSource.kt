package br.com.popularmovies.core.api

import androidx.paging.PagingSource
import br.com.popularmovies.core.api.models.movie.MovieTable
import br.com.popularmovies.core.api.models.movie.MovieTypeTable
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    fun getMoviesPagingSourceFactory(type: MovieTypeTable): PagingSource<Int, MovieTable>

    fun getMovies(type: MovieTypeTable): Flow<List<MovieTable>>

    suspend fun deleteAllMovies(type: MovieTypeTable)
    suspend fun insertAllMovies(movies: List<MovieTable>)
    suspend fun getMovie(movieId: Long): MovieTable?
}
