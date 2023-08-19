package br.com.popularmovies.core.api

import androidx.paging.PagingSource
import br.com.popularmovies.core.api.models.movie.MovieTable
import br.com.popularmovies.core.api.models.movie.MovieTypeTable

interface MovieLocalDataSource {
    fun getMoviesPagingSourceFactory(type: MovieTypeTable): PagingSource<Int, MovieTable>
    suspend fun deleteAllMovies(type: MovieTypeTable)
    suspend fun insertAllMovies(movies: List<MovieTable>)
}
