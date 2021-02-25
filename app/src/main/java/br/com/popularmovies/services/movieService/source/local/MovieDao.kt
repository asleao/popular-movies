package br.com.popularmovies.services.movieService.source.local

import androidx.room.*
import br.com.popularmovies.services.movieService.response.MovieDto

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    suspend fun movies(): List<MovieDto>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    suspend fun getMovie(movieId: Int): MovieDto

    @Query("SELECT * FROM movie where isFavorite=:isFavorite")
    suspend fun getFavoriteMovies(isFavorite: Boolean): List<MovieDto>

    @Query("SELECT EXISTS(SELECT * FROM movie WHERE id = :movieId)")
    suspend fun isMovieExists(movieId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movieDtos: List<MovieDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieDto: MovieDto)

    @Delete
    suspend fun deleteMovie(movieDto: MovieDto)

    @Query("UPDATE movie SET isFavorite = :status WHERE id = :movieId")
    suspend fun saveFavorites(movieId: Int, status: Boolean)
}
