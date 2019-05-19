package br.com.popularmovies.moviedetail.reviews.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.popularmovies.R;
import br.com.popularmovies.services.movieService.response.MovieReview;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final List<MovieReview> mReviews;

    public ReviewAdapter(List<MovieReview> reviews) {
        this.mReviews = reviews;
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.movie_comment, viewGroup, false);
        return new ReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder movieViewHolder, int position) {
        MovieReview movieReview = mReviews.get(position);
        movieViewHolder.mAuthor.setText(("Author: ").concat(movieReview.getAuthor()));
        movieViewHolder.mContent.setText(movieReview.getContent());

    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        private final TextView mAuthor;
        private final TextView mContent;

        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            mAuthor = itemView.findViewById(R.id.tv_comment_author);
            mContent = itemView.findViewById(R.id.tv_comment_message);
        }
    }
}
