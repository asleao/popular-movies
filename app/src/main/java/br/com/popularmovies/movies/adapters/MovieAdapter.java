package br.com.popularmovies.movies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.popularmovies.R;
import br.com.popularmovies.movies.data.response.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private List<Movie> mMovies;
    private final String IMAGE_URL = "http://image.tmdb.org/t/p/w185";

    public MovieAdapter(List<Movie> movies) {
        mMovies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        Movie movie = mMovies.get(position);
        Picasso.get()
                .load(IMAGE_URL + movie.getPoster())
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_photo)
                .into(movieViewHolder.getMoviePoster());
    }


    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}

class MovieViewHolder extends RecyclerView.ViewHolder {
    private ImageView mMoviePoster;

    MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        mMoviePoster = itemView.findViewById(R.id.iv_movie);
    }

    ImageView getMoviePoster() {
        return mMoviePoster;
    }

}
