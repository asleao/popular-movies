package br.com.popularmovies.data.local.typeconverters;

import androidx.room.TypeConverter;

import org.joda.time.LocalDate;

public class LocalDateConverter {
    @TypeConverter
    public static LocalDate toLocalDate(String date) {
        return date == null ? null : LocalDate.parse(date);
    }

    @TypeConverter
    public static String toString(LocalDate date) {
        return date == null ? null : date.toString();
    }
}
