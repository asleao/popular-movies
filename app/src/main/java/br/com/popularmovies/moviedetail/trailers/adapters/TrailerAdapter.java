package br.com.popularmovies.moviedetail.trailers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.popularmovies.R;
import br.com.popularmovies.services.movieService.response.MovieTrailer;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ReviewViewHolder> {

    private List<MovieTrailer> mTrailers;
    final private TrailerClickListener mOnTrailerClickListener;

    public TrailerAdapter(List<MovieTrailer> trailers, TrailerClickListener mOnTrailerClickListener) {
        this.mTrailers = trailers;
        this.mOnTrailerClickListener = mOnTrailerClickListener;
    }

    @NonNull
    @Override
    public TrailerAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.movie_trailer, viewGroup, false);
        return new TrailerAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.ReviewViewHolder movieViewHolder, int position) {
        final MovieTrailer movieTrailer = mTrailers.get(position);
        movieViewHolder.mTitle.setText(movieTrailer.getName());
        movieViewHolder.mMediaPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnTrailerClickListener.onPlay(movieTrailer.getKey());
            }
        });
        movieViewHolder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnTrailerClickListener.onShare("http://www.youtube.com/watch?v=" + movieTrailer.getKey());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        private ImageView mMediaPlay;
        private TextView mTitle;
        private ImageView mShare;

        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            mMediaPlay = itemView.findViewById(R.id.iv_trailer_play);
            mTitle = itemView.findViewById(R.id.tv_trailer_title);
            mShare = itemView.findViewById(R.id.iv_trailer_share);
        }
    }
}
