package br.com.popularmovies.moviedetail.trailers.adapters;

public interface TrailerClickListener {
    void onPlay(String videoUrl);

    void onShare(String videoSite);
}
