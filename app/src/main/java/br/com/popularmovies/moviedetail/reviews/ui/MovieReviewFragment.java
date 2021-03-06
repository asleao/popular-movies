package br.com.popularmovies.moviedetail.reviews.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.popularmovies.R;
import br.com.popularmovies.base.interfaces.IConection;
import br.com.popularmovies.data.model.ErrorResponse;
import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.moviedetail.reviews.adapters.ReviewAdapter;
import br.com.popularmovies.moviedetail.reviews.viewModel.MovieReviewViewModel;
import br.com.popularmovies.moviedetail.reviews.viewModel.factories.MovieReviewFactory;
import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.source.MovieRepository;
import br.com.popularmovies.services.movieService.source.local.MovieLocalDataSource;
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource;

import static br.com.popularmovies.data.Constants.NETWORK_ERROR_CODE;
import static br.com.popularmovies.movies.Constants.GENERIC_MSG_ERROR_TITLE;
import static br.com.popularmovies.movies.Constants.MOVIE_ID;

public class MovieReviewFragment extends Fragment implements IConection {

    private MovieReviewViewModel mViewModel;
    private RecyclerView mReviewsRecyclerView;
    private Observer<Resource<MovieReviews>> reviewsObserver;
    private Group mNoConnectionGroup;
    private Button mTryAgainButton;
    private TextView mNoConnectionText;
    private ProgressBar mProgressBar;
    private TextView mNoReviews;

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
                                if (movieReviewsResource.data.getReviews().isEmpty()) {
                                    showNoReviews();
                                } else {
                                    ReviewAdapter mReviewAdapter = new ReviewAdapter(movieReviewsResource.data.getReviews());
                                    mReviewsRecyclerView.setAdapter(mReviewAdapter);
                                    showResult();

                                }
                            }
                            break;
                        case ERROR:
                            hideLoading();
                            ErrorResponse error = movieReviewsResource.error;
                            if (error != null) {
                                if (error.getStatusCode() == NETWORK_ERROR_CODE) {
                                    showNoConnection(error.getStatusMessage());
                                } else {
                                    showGenericError(error.getStatusMessage());
                                }
                            }
                            break;
                    }
            }
        };
    }

    private void showNoReviews() {
        mNoReviews.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.movie_review_fragment, container, false);
        Bundle args = getArguments();
        if (args != null) {
            int movieId = args.getInt(MOVIE_ID, -1);
            MovieRepository mMovieRepository = MovieRepository.getInstance(MovieLocalDataSource.getInstance(requireActivity().getApplicationContext())
                    , MovieRemoteDataSource.getInstance());
            mViewModel = ViewModelProviders.of(this,
                    new MovieReviewFactory(mMovieRepository, movieId)).get(MovieReviewViewModel.class);
            setupFields(view);
            setupReviewsList(view);
            mViewModel.getReviews().observe(getViewLifecycleOwner(), reviewsObserver);
            mTryAgainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tryAgain();
                }
            });
        }
        return view;
    }

    private void setupFields(View view) {
        mNoConnectionGroup = view.findViewById(R.id.group_no_connection);
        mNoConnectionText = view.findViewById(R.id.tv_no_conection);
        mTryAgainButton = view.findViewById(R.id.bt_try_again);
        mProgressBar = view.findViewById(R.id.pb_base);
        mNoReviews = view.findViewById(R.id.tv_no_reviews);
    }

    private void setupReviewsList(View view) {
        mReviewsRecyclerView = view.findViewById(R.id.rv_reviews);
        mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mNoConnectionGroup.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showResult() {
        mNoConnectionGroup.setVisibility(View.GONE);
        mNoReviews.setVisibility(View.GONE);
    }

    @Override
    public void showNoConnection(String message) {
        mReviewsRecyclerView.setVisibility(View.GONE);
        mNoConnectionText.setText(message);
        mNoConnectionGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void showGenericError(String message) {
        final AlertDialog sortDialog = new AlertDialog.Builder(getContext())
                .setTitle(GENERIC_MSG_ERROR_TITLE)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_ok, null)
                .create();

        sortDialog.show();
    }

    @Override
    public void tryAgain() {
        mViewModel.tryAgain();
    }
}
