package br.com.popularmovies.moviedetail.trailers.adapters;

public interface TrailerClickListener {
    void onPlay(String videoId);

    void onShare(String videoSite);
}
