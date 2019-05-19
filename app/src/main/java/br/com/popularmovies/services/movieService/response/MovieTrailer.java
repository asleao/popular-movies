package br.com.popularmovies.services.movieService.response;

import com.squareup.moshi.Json;

public class MovieTrailer {
    @Json(name = "id")
    private String id;

    @Json(name = "iso_639_1")
    private String iso6391;

    @Json(name = "iso_3166_1")
    private String iso31661;

    @Json(name = "key")
    private String key;

    @Json(name = "name")
    private String name;

    @Json(name = "site")
    private String site;

    @Json(name = "size")
    private String size;

    @Json(name = "type")
    private String type;

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
