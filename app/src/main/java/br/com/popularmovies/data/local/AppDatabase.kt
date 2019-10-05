package br.com.popularmovies.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.popularmovies.data.local.typeconverters.BigDecimalConverter
import br.com.popularmovies.data.local.typeconverters.LocalDateConverter
import br.com.popularmovies.services.movieService.response.Movie
import br.com.popularmovies.services.movieService.source.local.MovieDao

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class, BigDecimalConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        private val LOCK = Any()
        private const val DATABASE_NAME = "popularmovies"

        fun getInstance(context: Context): AppDatabase {
            return synchronized(LOCK) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, DATABASE_NAME
                ).build()
            }
        }
    }
}
