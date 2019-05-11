package br.com.popularmovies.data.local.typeconverters;

import androidx.room.TypeConverter;

import java.math.BigDecimal;

public class BigDecimalConverter {
    @TypeConverter
    public static BigDecimal toBigDecimal(String number) {
        return number == null ? null : new BigDecimal(number);
    }

    @TypeConverter
    public static String toString(BigDecimal number) {
        return number == null ? null : number.toString();
    }
}
