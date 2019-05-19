package br.com.popularmovies.movies.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.popularmovies.R;
import br.com.popularmovies.services.movieService.response.Movie;

import static br.com.popularmovies.movies.Constants.IMAGE_URL;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> mMovies;
    final private MovieClickListener mOnMovieClickListener;

    public MovieAdapter(List<Movie> movies, MovieClickListener mOnMovieClickListener) {
        this.mMovies = movies;
        this.mOnMovieClickListener = mOnMovieClickListener;
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

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mMoviePoster;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mMoviePoster = itemView.findViewById(R.id.iv_movie);
            itemView.setOnClickListener(this);
        }

        ImageView getMoviePoster() {
            return mMoviePoster;
        }

        @Override
        public void onClick(View v) {
            mOnMovieClickListener.onMovieClick(mMovies.get(getAdapterPosition()));

        }
    }
}
