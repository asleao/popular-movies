package br.com.popularmovies.moviedetail.trailers.adapters;

public interface TrailerClickListener {
    void onPlay(String videoKey);

    void onShare(String videoUrl);
}
