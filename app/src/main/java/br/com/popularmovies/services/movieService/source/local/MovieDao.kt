package br.com.popularmovies.services.movieService.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import br.com.popularmovies.services.movieService.response.Movie

@Dao
interface MovieDao {

    @get:Query("SELECT * FROM movie")
    val movies: LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovie(movieId: Int): LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)

    @Query("UPDATE movie SET isFavorite = :status WHERE id = :movieId")
    fun saveFavorites(movieId: Int, status: Boolean)
}
