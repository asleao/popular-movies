package br.com.popularmovies.moviedetail.trailers.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import br.com.popularmovies.moviedetail.trailers.adapters.TrailerAdapter;
import br.com.popularmovies.moviedetail.trailers.adapters.TrailerClickListener;
import br.com.popularmovies.moviedetail.trailers.viewmodel.MovieTrailerViewModel;
import br.com.popularmovies.moviedetail.trailers.viewmodel.factories.MovieTrailerFactory;
import br.com.popularmovies.services.movieService.response.MovieTrailers;
import br.com.popularmovies.services.movieService.source.MovieRepository;
import br.com.popularmovies.services.movieService.source.local.MovieLocalDataSource;
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource;

import static br.com.popularmovies.data.Constants.NETWORK_ERROR_CODE;
import static br.com.popularmovies.movies.Constants.GENERIC_MSG_ERROR_TITLE;
import static br.com.popularmovies.movies.Constants.MOVIE_ID;

public class MovieTrailerFragment extends Fragment implements IConection, TrailerClickListener {

    private MovieTrailerViewModel mViewModel;
    private RecyclerView mTrailersRecyclerView;
    private Observer<Resource<MovieTrailers>> trailersObserver;
    private Group mNoConnectionGroup;
    private Button mTryAgainButton;
    private TextView mNoConnectionText;
    private ProgressBar mProgressBar;
    private TextView mNoTrailers;

    public static MovieTrailerFragment newInstance(int movieId) {
        MovieTrailerFragment movieTrailerFragment = new MovieTrailerFragment();
        Bundle args = new Bundle();
        args.putInt(MOVIE_ID, movieId);
        movieTrailerFragment.setArguments(args);
        return movieTrailerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupObservers();
    }

    private void setupObservers() {
        trailersObserver = new Observer<Resource<MovieTrailers>>() {
            @Override
            public void onChanged(@Nullable Resource<MovieTrailers> movieReviewsResource) {
                if (movieReviewsResource != null)
                    switch (movieReviewsResource.status) {
                        case LOADING:
                            showLoading();
                            mTrailersRecyclerView.setVisibility(View.GONE);
                            break;
                        case SUCCESS:
                            hideLoading();
                            mTrailersRecyclerView.setVisibility(View.VISIBLE);
                            if (movieReviewsResource.data != null) {
                                if (movieReviewsResource.data.getTrailers().isEmpty()) {
                                    showNoTrailers();
                                } else {
                                    TrailerAdapter mTrailerAdapter = new TrailerAdapter(movieReviewsResource.data.getTrailers(), MovieTrailerFragment.this);
                                    mTrailersRecyclerView.setAdapter(mTrailerAdapter);
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

    private void showNoTrailers() {
        mNoTrailers.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_trailer_fragment, container, false);
        Bundle args = getArguments();
        if (args != null) {
            int movieId = args.getInt(MOVIE_ID, -1);
            MovieRepository mMovieRepository = MovieRepository.getInstance(MovieLocalDataSource.getInstance(requireActivity().getApplicationContext())
                    , MovieRemoteDataSource.getInstance());
            mViewModel = ViewModelProviders.of(this,
                    new MovieTrailerFactory(mMovieRepository, movieId)).get(MovieTrailerViewModel.class);
            setupFields(view);
            setupTrailersList(view);
            mViewModel.getTrailers().observe(getViewLifecycleOwner(), trailersObserver);
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
        mNoTrailers = view.findViewById(R.id.tv_no_trailers);
    }

    private void setupTrailersList(View view) {
        mTrailersRecyclerView = view.findViewById(R.id.rv_trailers);
        mTrailersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        mNoTrailers.setVisibility(View.GONE);
    }

    @Override
    public void showNoConnection(String message) {
        mTrailersRecyclerView.setVisibility(View.GONE);
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

    @Override
    public void onPlay(String videoUrl) {
        Toast.makeText(requireContext(), "onPlay", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShare(String videoSite) {
        Toast.makeText(requireContext(), "onShare", Toast.LENGTH_SHORT).show();
    }
}
