package br.com.popularmovies.moviedetail.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import br.com.popularmovies.R;
import br.com.popularmovies.base.interfaces.IConection;
import br.com.popularmovies.data.model.ErrorResponse;
import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.moviedetail.reviews.ui.MovieReviewFragment;
import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel;
import br.com.popularmovies.moviedetail.viewmodel.factories.MovieDetailFactory;
import br.com.popularmovies.services.movieService.response.Movie;
import br.com.popularmovies.services.movieService.source.MovieRepository;
import br.com.popularmovies.services.movieService.source.local.MovieLocalDataSource;
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource;
import br.com.popularmovies.utils.FragmentUtils;

import static br.com.popularmovies.data.Constants.NETWORK_ERROR_CODE;
import static br.com.popularmovies.movies.Constants.GENERIC_MSG_ERROR_TITLE;
import static br.com.popularmovies.movies.Constants.IMAGE_URL;
import static br.com.popularmovies.movies.Constants.MOVIE;
import static br.com.popularmovies.movies.Constants.MOVIE_DATE_PATTERN;
import static br.com.popularmovies.movies.Constants.NO_REVIEWS_MSG_ERROR_MESSAGE;
import static br.com.popularmovies.movies.Constants.NO_REVIEWS_MSG_ERROR_TITLE;

public class MovieDetailFragment extends Fragment implements IConection {

    private MovieDetailViewModel mViewModel;
    private Movie mMovieFromIntent;
    private TextView mMovieTitle;
    private ImageView mMoviePoster;
    private TextView mMovieReleaseDate;
    private TextView mMovieRating;
    private TextView mMovieOverview;
    private TextView mReviews;
    private AppCompatImageView mFavorites;
    private Observer<Resource<Void>> favorites;
    private Observer<Resource<Movie>> movie;
    private Group mNoConnectionGroup;
    private Group mMovieDetailGroup;
    private Button mTryAgainButton;
    private TextView mNoConnectionText;
    private ProgressBar mProgressBar;


    static MovieDetailFragment newInstance() {
        return new MovieDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupObservers();
    }

    private void setupObservers() {
        movie = new Observer<Resource<Movie>>() {
            @Override
            public void onChanged(Resource<Movie> movieResource) {
                switch (movieResource.status) {
                    case LOADING:
                        showLoading();
                        break;
                    case SUCCESS:
                        hideLoading();
                        showResult();
                        if (movieResource.data != null) {
                            showMovieDetails(movieResource.data);
                            mViewModel.setMovie(movieResource.data);
                            setFavoritesImage(movieResource.data.isFavorite());
                        }
                        break;
                    case ERROR:
                        hideLoading();
                        ErrorResponse error = movieResource.error;
                        if (error != null) {
                            if (error.getStatusCode() == NETWORK_ERROR_CODE) {
                                mViewModel.setMovie(mMovieFromIntent);
                                showMovieDetails(mMovieFromIntent);
                                setFavoritesImage(mMovieFromIntent.isFavorite());
                            } else {
                                showGenericError(error.getStatusMessage());
                            }
                        }
                        break;
                }
            }
        };
        favorites = new Observer<Resource<Void>>() {
            @Override
            public void onChanged(Resource<Void> resource) {
                if (resource != null) {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            setFavoritesImage(mViewModel.getMovie().isFavorite());
                            break;
                        case ERROR:
                            break;
                    }
                }
            }
        };
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_detail_fragment, container, false);
        Intent intent = requireActivity().getIntent();
        setupFields(view);
        setData(intent);
        setupViewModel();
        mViewModel.getFavorites().observe(getViewLifecycleOwner(), favorites);
        mViewModel.getmMovie().observe(getViewLifecycleOwner(), movie);
        mFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.saveFavorites(!mViewModel.getMovie().isFavorite());
            }
        });
        mReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMovieFromIntent.getId() != -1) {
                    FragmentUtils.replaceFragmentInActivity(requireFragmentManager(),
                            MovieReviewFragment.newInstance(mMovieFromIntent.getId()),
                            R.id.fg_moviedetail,
                            getResources().getString(R.string.fg_movie_review_tag),
                            true);
                } else {
                    showNoReviewsDialog();
                }
            }
        });
        mTryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryAgain();
            }
        });
        return view;
    }

    private void setupViewModel() {
        MovieRepository mMovieRepository = MovieRepository.getInstance(MovieLocalDataSource.getInstance(requireActivity().getApplicationContext())
                , MovieRemoteDataSource.getInstance());
        mViewModel = ViewModelProviders.of(this,
                new MovieDetailFactory(mMovieRepository, mMovieFromIntent.getId())).get(MovieDetailViewModel.class);
    }

    private void showNoReviewsDialog() {
        final AlertDialog noReviewsDialog = new AlertDialog.Builder(getContext())
                .setTitle(NO_REVIEWS_MSG_ERROR_TITLE)
                .setMessage(NO_REVIEWS_MSG_ERROR_MESSAGE)
                .setPositiveButton(R.string.dialog_ok, null)
                .create();

        noReviewsDialog.show();
    }

    private void setupFields(View view) {
        mMovieTitle = view.findViewById(R.id.tv_movie_title);
        mMoviePoster = view.findViewById(R.id.iv_movie_poster);
        mMovieReleaseDate = view.findViewById(R.id.tv_movie_release_date);
        mMovieRating = view.findViewById(R.id.tv_movie_rating);
        mMovieOverview = view.findViewById(R.id.tv_movie_overview);
        mReviews = view.findViewById(R.id.tv_movie_reviews_label);
        mFavorites = view.findViewById(R.id.iv_favorite);
        mNoConnectionGroup = view.findViewById(R.id.group_no_connection);
        mMovieDetailGroup = view.findViewById(R.id.group_movie_detail);
        mNoConnectionText = view.findViewById(R.id.tv_no_conection);
        mTryAgainButton = view.findViewById(R.id.bt_try_again);
        mProgressBar = view.findViewById(R.id.pb_base);
    }

    private void setData(Intent intent) {
        mMovieFromIntent = intent.getParcelableExtra(MOVIE);
    }

    private void showMovieDetails(Movie movie) {
        mMovieTitle.setText(movie.getOriginalTitle() == null ?
                "" : movie.getOriginalTitle());
        String imageUrl = movie.getPoster() == null ?
                "" : IMAGE_URL + movie.getPoster();
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_photo)
                .into(mMoviePoster);
        mMovieReleaseDate.setText(movie.getReleaseDate() == null ?
                "None" : movie.getReleaseDate().toString(MOVIE_DATE_PATTERN, Locale.getDefault()));
        mMovieRating.setText(movie.getVoteAverage() == null ?
                "" : movie.getVoteAverage().toString());
        mMovieOverview.setText(movie.getOverview() == null ?
                "" : movie.getOverview());
    }

    private void setFavoritesImage(boolean isFavorite) {
        if (isFavorite) {
            mFavorites.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_black_24dp));
        } else {
            mFavorites.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_border_black_24dp));
        }
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mNoConnectionGroup.setVisibility(View.GONE);
        mMovieDetailGroup.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showResult() {
        mNoConnectionGroup.setVisibility(View.GONE);
        mMovieDetailGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoConnection(String message) {
        mMovieDetailGroup.setVisibility(View.GONE);
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
