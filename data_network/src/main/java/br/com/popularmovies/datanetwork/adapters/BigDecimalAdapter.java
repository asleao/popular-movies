package br.com.popularmovies.datanetwork.adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.math.BigDecimal;

public class BigDecimalAdapter {
    @ToJson
    String toJson(BigDecimal number) {
        return number.toString();
    }

    @FromJson
    BigDecimal fromJson(String number) {
        return (number == null || number.isEmpty()) ?
                new BigDecimal("") :
                new BigDecimal(number);
    }
}
