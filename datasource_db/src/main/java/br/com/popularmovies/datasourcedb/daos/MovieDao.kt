package br.com.popularmovies.datasourcedb.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.popularmovies.datasourcedb.models.movie.MovieTable

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_popular")
    fun mostPopularMovies(): PagingSource<Int, MovieTable.MostPopular>

    @Query("SELECT * FROM movie_top_rated")
    fun topRatedMovies(): PagingSource<Int, MovieTable.TopRated>

    @Query("SELECT * FROM movie_now_playing")
    fun nowPlayingMovies(): PagingSource<Int, MovieTable.NowPlaying>

//    @Query("SELECT * FROM movie_popular WHERE id = :movieId") //TODO Check that
//    suspend fun getMovie(movieId: Long): MovieTable
//
//    @Query("SELECT * FROM movie_popular where isFavorite=:isFavorite") //TODO Check that
//    suspend fun getFavoriteMovies(isFavorite: Boolean): List<MovieTable>

//    @Query("SELECT EXISTS(SELECT * FROM movie_popular WHERE id = :movieId)") //TODO Check that
//    suspend fun isMovieExists(movieId: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMostPopularMovies(movieTables: List<MovieTable.MostPopular>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTopRatedMovies(movieTables: List<MovieTable.TopRated>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNowPlayingMovies(movieTables: List<MovieTable.NowPlaying>)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertMovie(movieTable: MovieTable)
//
//    @Query("UPDATE movie_popular SET isFavorite = :status WHERE id = :movieId") //TODO Check that
//    suspend fun saveFavorites(movieId: Long, status: Boolean)

    @Query("DELETE FROM movie_popular")
    suspend fun deleteAllMostPopularMovies()

    @Query("DELETE FROM movie_top_rated")
    suspend fun deleteAllTopRatedMovies()

    @Query("DELETE FROM movie_now_playing")
    suspend fun deleteAllNowPlayingMovies()
}
