package br.com.popularmovies.data.local.typeconverters

import androidx.room.TypeConverter

import java.math.BigDecimal

class BigDecimalConverter {
    @TypeConverter
    fun fromLong(number: Long?): BigDecimal? {
        return number?.let {
            BigDecimal(it).movePointLeft(2).setScale(
                1,
                BigDecimal.ROUND_HALF_EVEN
            )
        } ?: run { null }
    }

    @TypeConverter
    fun toLong(number: BigDecimal?): Long? {
        return number?.multiply(BigDecimal(100))?.toLong()
    }
}
