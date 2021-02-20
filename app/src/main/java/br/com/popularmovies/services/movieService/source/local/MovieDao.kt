package br.com.popularmovies.services.movieService.source.local

import androidx.room.*
import br.com.popularmovies.services.movieService.response.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun movies(): List<Movie>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovie(movieId: Int): Movie

    @Query("SELECT * FROM movie where isFavorite=:isFavorite")
    fun getFavoriteMovies(isFavorite: Boolean): List<Movie>

    @Query("SELECT EXISTS(SELECT * FROM movie WHERE id = :movieId)")
    fun isMovieExists(movieId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)

    @Query("UPDATE movie SET isFavorite = :status WHERE id = :movieId")
    fun saveFavorites(movieId: Int, status: Boolean)
}
