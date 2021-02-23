package br.com.popularmovies.services.movieService.source.local

import androidx.room.*
import br.com.popularmovies.services.movieService.response.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    suspend fun movies(): List<Movie>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    suspend fun getMovie(movieId: Int): Movie

    @Query("SELECT * FROM movie where isFavorite=:isFavorite")
    suspend fun getFavoriteMovies(isFavorite: Boolean): List<Movie>

    @Query("SELECT EXISTS(SELECT * FROM movie WHERE id = :movieId)")
    suspend fun isMovieExists(movieId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("UPDATE movie SET isFavorite = :status WHERE id = :movieId")
    suspend fun saveFavorites(movieId: Int, status: Boolean)
}
