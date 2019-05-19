package br.com.popularmovies.data.local.typeconverters;

import androidx.room.TypeConverter;

import java.math.BigDecimal;

public class BigDecimalConverter {
    @TypeConverter
    public static BigDecimal fromLong(Long number) {
        return number == null ? null : new BigDecimal(number).movePointLeft(2).setScale(1, BigDecimal.ROUND_HALF_EVEN);
    }

    @TypeConverter
    public static Long toLong(BigDecimal number) {
        return number == null ? null : number.multiply(new BigDecimal(100)).longValue();
    }
}
