package br.com.popularmovies.services.movieService.source.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.popularmovies.services.movieService.response.Movie;

@Dao
public interface MovieDao {

    //TODO Add sorting
    @Query("SELECT * FROM movie ORDER BY popularity desc")
    LiveData<List<Movie>> getMovies();

    @Insert
    void insertMovie(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllMovies(List<Movie> movies);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("UPDATE movie SET isFavorite = :status WHERE id = :movieId")
    void saveFavorites(int movieId, boolean status);
}
