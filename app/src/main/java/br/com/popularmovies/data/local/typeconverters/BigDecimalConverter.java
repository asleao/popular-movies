package br.com.popularmovies.data.local.typeconverters;

import androidx.room.TypeConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalConverter {
    @TypeConverter
    public static BigDecimal fromLong(Long number) {
        return number == null ? null : new BigDecimal(number).divide(new BigDecimal(100), RoundingMode.HALF_EVEN);
    }

    @TypeConverter
    public static Long toLong(BigDecimal number) {
        return number == null ? null : number.multiply(new BigDecimal(100)).longValue();
    }
}
