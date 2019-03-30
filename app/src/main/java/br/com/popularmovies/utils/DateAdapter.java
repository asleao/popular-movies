package br.com.popularmovies.utils;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import org.joda.time.LocalDate;

public class DateAdapter {
    private String datePattern;


    public DateAdapter(String datePattern) {
        this.datePattern = datePattern;
    }

    @ToJson
    String toJson(LocalDate date) {
        return date.toString();
    }

    @FromJson
    LocalDate fromJson(String date) {
        return date.isEmpty() ? null : LocalDate.parse(date);
    }
}
