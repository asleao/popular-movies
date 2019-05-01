package br.com.popularmovies.moviedetail.reviews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.popularmovies.R;
import br.com.popularmovies.services.movieService.response.MovieReview;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<MovieReview> mReviews;
    final private ReviewAdapter.ReviewClickListener mOnReviewClickListener;

    public ReviewAdapter(List<MovieReview> reviews, ReviewAdapter.ReviewClickListener mOnReviewClickListener) {
        this.mReviews = reviews;
        this.mOnReviewClickListener = mOnReviewClickListener;
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

    public interface ReviewClickListener {
        void onReviewClick(MovieReview movieReview);
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mAuthor;
        private TextView mContent;

        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            mAuthor = itemView.findViewById(R.id.tv_comment_author);
            mContent = itemView.findViewById(R.id.tv_comment_message);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnReviewClickListener.onReviewClick(mReviews.get(getAdapterPosition()));

        }
    }
}
