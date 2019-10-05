package br.com.popularmovies.core.network.local.typeconverters

import androidx.room.TypeConverter

import org.joda.time.LocalDate

class LocalDateConverter {
    @TypeConverter
    fun toLocalDate(date: String?): LocalDate? {
        return date.let {
            LocalDate.parse(date)
        } ?: run { null }
    }

    @TypeConverter
    fun toString(date: LocalDate?): String? {
        return date?.toString()
    }
}
