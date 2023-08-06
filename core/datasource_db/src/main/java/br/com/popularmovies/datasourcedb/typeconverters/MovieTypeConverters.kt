package br.com.popularmovies.datasourcedb.typeconverters

import androidx.room.TypeConverter
import br.com.popularmovies.datasourcedb.models.movie.MovieTypeTable

class MovieTypeConverters {

    @TypeConverter
    fun toMovieType(id: Int?): MovieTypeTable? {
        return id?.run { MovieTypeTable.forId(id) }
    }

    @TypeConverter
    fun fromMovieType(status: MovieTypeTable?): Int? {
        return status?.id
    }
}