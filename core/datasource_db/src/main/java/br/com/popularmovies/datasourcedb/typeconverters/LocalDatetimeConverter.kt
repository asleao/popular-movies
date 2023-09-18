package br.com.popularmovies.datasourcedb.typeconverters

import androidx.room.TypeConverter
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime

class LocalDatetimeConverter {
    @TypeConverter
    fun toLocalDate(dateTime: String?): LocalDateTime? {
        return dateTime.let {
            LocalDateTime.parse(dateTime)
        } ?: run { null }
    }

    @TypeConverter
    fun toString(dateTime: LocalDateTime?): String? {
        return dateTime?.toString()
    }
}
