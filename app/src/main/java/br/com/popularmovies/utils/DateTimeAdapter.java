package br.com.popularmovies.utils;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeAdapter {
    private String datePattern;

    public DateTimeAdapter(String datePattern) {
        this.datePattern = datePattern;
    }

    @ToJson
    String toJson(DateTime dateTime) {
        return dateTime.toString();
    }

    @FromJson
    DateTime fromJson(String dateTime) {
        DateTime dateTimeFormated;
        if (dateTime == null || dateTime.isEmpty()) {
            dateTimeFormated = new DateTime("");
        } else {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(datePattern);
            dateTimeFormated = formatter.parseDateTime(dateTime);
            new DateTime(dateTimeFormated);
        }

        return dateTimeFormated;
    }
}
