package br.com.popularmovies.moviedetail.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import br.com.popularmovies.R;
import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.moviedetail.reviews.ui.MovieReviewFragment;
import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel;
import br.com.popularmovies.moviedetail.viewmodel.factories.MovieDetailFactory;
import br.com.popularmovies.services.movieService.response.Movie;
import br.com.popularmovies.services.movieService.source.MovieRepository;
import br.com.popularmovies.services.movieService.source.local.MovieLocalDataSource;
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource;
import br.com.popularmovies.utils.FragmentUtils;

import static br.com.popularmovies.movies.Constants.IMAGE_URL;
import static br.com.popularmovies.movies.Constants.MOVIE;
import static br.com.popularmovies.movies.Constants.MOVIE_DATE_PATTERN;
import static br.com.popularmovies.movies.Constants.NO_REVIEWS_MSG_ERROR_MESSAGE;
import static br.com.popularmovies.movies.Constants.NO_REVIEWS_MSG_ERROR_TITLE;

public class MovieDetailFragment extends Fragment {

    private MovieDetailViewModel mViewModel;
    private Movie mMovie;
    private int mMovieId;
    private TextView mMovieTitle;
    private ImageView mMoviePoster;
    private TextView mMovieReleaseDate;
    private TextView mMovieRating;
    private TextView mMovieOverview;
    private TextView mReviews;
    private AppCompatImageView mFavorites;
    private Observer<Resource<Boolean>> favorites;


    static MovieDetailFragment newInstance() {
        return new MovieDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupObservers();
    }

    private void setupObservers() {
        favorites = new Observer<Resource<Boolean>>() {
            @Override
            public void onChanged(Resource<Boolean> resource) {
                if (resource != null) {
                    switch (resource.status) {
                        case SUCCESS:
                            if (resource.data != null) {
                                mMovie.setFavorite(resource.data);
                                setFavoritesImage(mMovie.isFavorite());
                            }
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
        setupFields(view);
        mReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMovieId != -1) {
                    FragmentUtils.replaceFragmentInActivity(requireFragmentManager(),
                            MovieReviewFragment.newInstance(mMovieId),
                            R.id.fg_moviedetail,
                            getResources().getString(R.string.fg_movie_review_tag),
                            true);
                } else {
                    showNoReviewsDialog();
                }
            }
        });
        Intent intent = requireActivity().getIntent();
        setData(intent);
        setupViewModel();
        mViewModel.getIsFavorite().observe(getViewLifecycleOwner(), favorites);
        mFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.saveFavorites(!mMovie.isFavorite());
            }
        });
        return view;
    }

    private void setupViewModel() {
        MovieRepository mMovieRepository = MovieRepository.getInstance(MovieLocalDataSource.getInstance(requireActivity().getApplicationContext())
                , MovieRemoteDataSource.getInstance());
        mViewModel = ViewModelProviders.of(this,
                new MovieDetailFactory(mMovieRepository, mMovieId)).get(MovieDetailViewModel.class);
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
    }

    private void setData(Intent intent) {
        mMovie = intent.getParcelableExtra(MOVIE);

        mMovieId = mMovie.getId();
        mMovieTitle.setText(mMovie.getOriginalTitle() == null ?
                "" : mMovie.getOriginalTitle());
        String imageUrl = mMovie.getPoster() == null ?
                "" : IMAGE_URL + mMovie.getPoster();
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_photo)
                .into(mMoviePoster);
        mMovieReleaseDate.setText(mMovie.getReleaseDate() == null ?
                "None" : mMovie.getReleaseDate().toString(MOVIE_DATE_PATTERN, Locale.getDefault()));
        mMovieRating.setText(mMovie.getVoteAverage() == null ?
                "" : mMovie.getVoteAverage().toString());
        mMovieOverview.setText(mMovie.getOverview() == null ?
                "" : mMovie.getOverview());
        setFavoritesImage(mMovie.isFavorite());
    }

    private void setFavoritesImage(boolean isFavorite) {
        if (isFavorite) {
            mFavorites.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_black_24dp));
        } else {
            mFavorites.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_border_black_24dp));
        }
    }
}
