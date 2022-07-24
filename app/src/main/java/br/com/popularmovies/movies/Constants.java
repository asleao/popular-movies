package br.com.popularmovies.movies;

public final class Constants {
    public static final String MOVIE_DATE_PATTERN = "dd MMM YYYY";
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/original"; //TODO Use /configuration endpoint to get sizes and extract from here
    public static final int INDEX_FILTER_MOST_POPULAR = 0;
    public static final int INDEX_FILTER_TOP_RATED = 1;
    public static final int INDEX_FILTER_FAVORITES = 2;
    public static final String TITLE_DIALOG_FILTER = "Sort By:";
    public static final String FILTER_MOST_POPULAR = "popular";
    public static final String FILTER_HIGHEST_RATED = "top_rated";
    public static final String FILTER_FAVORITES = "favorites";
}
