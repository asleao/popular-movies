package br.com.popularmovies.moviedetail.reviews.ui;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.popularmovies.R;
import br.com.popularmovies.base.BaseFragment;
import br.com.popularmovies.data.model.ErrorResponse;
import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.moviedetail.reviews.adapters.ReviewAdapter;
import br.com.popularmovies.moviedetail.reviews.viewModel.MovieReviewViewModel;
import br.com.popularmovies.moviedetail.reviews.viewModel.factories.MovieReviewFactory;
import br.com.popularmovies.services.movieService.response.MovieReviews;

import static br.com.popularmovies.movies.Constants.GENERIC_MSG_ERROR_TITLE;
import static br.com.popularmovies.movies.Constants.MOVIE_ID;

public class MovieReviewFragment extends BaseFragment {

    private MovieReviewViewModel mViewModel;
    private RecyclerView mReviewsRecyclerView;
    private Observer<Resource<MovieReviews>> reviewsObserver;

    public static MovieReviewFragment newInstance(int movieId) {
        MovieReviewFragment movieReviewFragment = new MovieReviewFragment();
        Bundle args = new Bundle();
        args.putInt(MOVIE_ID, movieId);
        movieReviewFragment.setArguments(args);

        return movieReviewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupObservers();
    }

    private void setupObservers() {
        reviewsObserver = new Observer<Resource<MovieReviews>>() {
            @Override
            public void onChanged(@Nullable Resource<MovieReviews> movieReviewsResource) {
                if (movieReviewsResource != null)
                    switch (movieReviewsResource.status) {
                        case LOADING:
                            showLoading();
                            mReviewsRecyclerView.setVisibility(View.GONE);
                            break;
                        case SUCCESS:
                            hideLoading();
                            mReviewsRecyclerView.setVisibility(View.VISIBLE);
                            if (movieReviewsResource.data != null) {
                                ReviewAdapter mReviewAdapter = new ReviewAdapter(movieReviewsResource.data.getReviews());
                                mReviewsRecyclerView.setAdapter(mReviewAdapter);
                                showResult();
                            }
                            break;
                        case ERROR:
                            mReviewsRecyclerView.setVisibility(View.GONE);
                            ErrorResponse error = movieReviewsResource.error;
                            if (error != null) {
                                if (error.getStatusCode() == 503) {
                                    showNoConnection(error.getStatusMessage());
//                                    tryAgain(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            mViewModel.tryAgain();
//                                        }
//                                    });
                                } else {
                                    showGenericError(error.getStatusMessage());
                                }
                            }
                            break;
                    }
            }
        };
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.movie_review_fragment, container, false);
        Bundle args = getArguments();
        if (args != null) {
            int movieId = args.getInt(MOVIE_ID, -1);
            mViewModel = ViewModelProviders.of(this,
                    new MovieReviewFactory(movieId)).get(MovieReviewViewModel.class);
            setupReviewsList(view);
            mViewModel.getReviews().observe(getViewLifecycleOwner(), reviewsObserver);
        }
        return view;
    }

    private void setupReviewsList(View view) {
        mReviewsRecyclerView = view.findViewById(R.id.rv_reviews);
        mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
