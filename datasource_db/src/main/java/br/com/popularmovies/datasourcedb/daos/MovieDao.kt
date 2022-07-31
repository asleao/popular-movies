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

    @Query("SELECT * FROM movies WHERE type = :type")
    fun movies(type: MovieTypeTable): PagingSource<Int, MovieTable>

//    @Query("SELECT * FROM movie_popular WHERE id = :movieId") //TODO Checfeaturek that
//    suspend fun getMovie(movieId: Long): MovieTable
//
//    @Query("SELECT * FROM movie_popular where isFavorite=:isFavorite") //TODO Check that
//    suspend fun getFavoriteMovies(isFavorite: Boolean): List<MovieTable>

//    @Query("SELECT EXISTS(SELECT * FROM movie_popular WHERE id = :movieId)") //TODO Check that
//    suspend fun isMovieExists(movieId: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movieTables: List<MovieTable>)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertMovie(movieTable: MovieTable)
//
//    @Query("UPDATE movie_popular SET isFavorite = :status WHERE id = :movieId") //TODO Check that
//    suspend fun saveFavorites(movieId: Long, status: Boolean)

    @Query("DELETE FROM movies WHERE type = :type")
    suspend fun deleteAllMovies(type: MovieTypeTable)
}
