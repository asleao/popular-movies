package br.com.popularmovies.datasourcedb.datasources.movie

import androidx.paging.PagingSource
import br.com.popularmovies.datasourcedb.models.movie.MovieTable
import br.com.popularmovies.datasourcedb.models.movie.MovieTypeTable

interface MovieLocalDataSource {
    fun getMoviesPagingSourceFactory(type: MovieTypeTable): PagingSource<Int, MovieTable>
    suspend fun deleteAllMovies(type: MovieTypeTable)
    suspend fun insertAllMovies(movies: List<MovieTable>)
}
