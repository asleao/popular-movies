package br.com.popularmovies.datasourcedb.daos

import androidx.room.*
import br.com.popularmovies.datasourcedb.models.movie.MovieTable

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    suspend fun movies(): List<MovieTable>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    suspend fun getMovie(movieId: Int): MovieTable

    @Query("SELECT * FROM movie where isFavorite=:isFavorite")
    suspend fun getFavoriteMovies(isFavorite: Boolean): List<MovieTable>

    @Query("SELECT EXISTS(SELECT * FROM movie WHERE id = :movieId)")
    suspend fun isMovieExists(movieId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movieTables: List<MovieTable>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieTable: MovieTable)

    @Delete
    suspend fun deleteMovie(movieTable: MovieTable)

    @Query("UPDATE movie SET isFavorite = :status WHERE id = :movieId")
    suspend fun saveFavorites(movieId: Int, status: Boolean)
}
