package br.com.popularmovies.datasourcedb.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.popularmovies.datasourcedb.models.movie.MovieTable
import br.com.popularmovies.datasourcedb.models.movie.MovieTypeTable

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie WHERE type=:type")
    fun movies(type: MovieTypeTable): PagingSource<Int, MovieTable>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    suspend fun getMovie(movieId: Long): MovieTable

    @Query("SELECT * FROM movie where isFavorite=:isFavorite")
    suspend fun getFavoriteMovies(isFavorite: Boolean): List<MovieTable>

    @Query("SELECT EXISTS(SELECT * FROM movie WHERE id = :movieId)")
    suspend fun isMovieExists(movieId: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movieTables: List<MovieTable>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieTable: MovieTable)

    @Query("UPDATE movie SET isFavorite = :status WHERE id = :movieId")
    suspend fun saveFavorites(movieId: Long, status: Boolean)

    @Query("DELETE FROM movie WHERE type=:type")
    suspend fun deleteAllMovies(type: MovieTypeTable)
}
