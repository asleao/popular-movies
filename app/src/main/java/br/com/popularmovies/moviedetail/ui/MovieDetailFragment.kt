package br.com.popularmovies.moviedetail.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.squareup.picasso.Picasso

import java.util.Locale

import br.com.popularmovies.R
import br.com.popularmovies.base.interfaces.IConection
import br.com.popularmovies.data.model.ErrorResponse
import br.com.popularmovies.data.model.Resource
import br.com.popularmovies.moviedetail.reviews.ui.MovieReviewFragment
import br.com.popularmovies.moviedetail.trailers.ui.MovieTrailerFragment
import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel
import br.com.popularmovies.moviedetail.viewmodel.factories.MovieDetailFactory
import br.com.popularmovies.services.movieService.response.Movie
import br.com.popularmovies.services.movieService.source.MovieRepository
import br.com.popularmovies.services.movieService.source.local.MovieLocalDataSource
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource
import br.com.popularmovies.utils.FragmentUtils

import br.com.popularmovies.data.Constants.NETWORK_ERROR_CODE
import br.com.popularmovies.movies.Constants.GENERIC_MSG_ERROR_TITLE
import br.com.popularmovies.movies.Constants.IMAGE_URL
import br.com.popularmovies.movies.Constants.MOVIE
import br.com.popularmovies.movies.Constants.MOVIE_DATE_PATTERN
import br.com.popularmovies.movies.Constants.NO_DATA_MSG_ERROR_TITLE
import br.com.popularmovies.movies.Constants.NO_REVIEWS_MSG_ERROR_MESSAGE
import br.com.popularmovies.movies.Constants.NO_TRAILER_MSG_ERROR_MESSAGE

class MovieDetailFragment : Fragment(), IConection {

    private var mViewModel: MovieDetailViewModel? = null
    private var mMovieFromIntent: Movie? = null
    private var mMovieTitle: TextView? = null
    private var mMoviePoster: ImageView? = null
    private var mMovieReleaseDate: TextView? = null
    private var mMovieRating: TextView? = null
    private var mMovieOverview: TextView? = null
    private var mReviews: TextView? = null
    private var mTrailers: TextView? = null
    private var mFavorites: AppCompatImageView? = null
    private var favorites: Observer<Resource<Void>>? = null
    private var movie: Observer<Resource<Movie>>? = null
    private var mNoConnectionGroup: Group? = null
    private var mMovieDetailGroup: Group? = null
    private var mTryAgainButton: Button? = null
    private var mNoConnectionText: TextView? = null
    private var mProgressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        movie = Observer { movieResource ->
            when (movieResource.status) {
                Resource.Status.LOADING -> showLoading()
                Resource.Status.SUCCESS -> {
                    hideLoading()
                    if (movieResource.data != null) {
                        showMovieDetails(movieResource.data)
                        mViewModel!!.movie = movieResource.data
                        setFavoritesImage(movieResource.data.isFavorite)
                    }
                    showResult()
                }
                Resource.Status.ERROR -> {
                    hideLoading()
                    val error = movieResource.error
                    if (error != null) {
                        if (error.statusCode == NETWORK_ERROR_CODE) {
                            mViewModel!!.movie = mMovieFromIntent
                            showMovieDetails(mMovieFromIntent!!)
                            setFavoritesImage(mMovieFromIntent!!.isFavorite)
                        } else {
                            showGenericError(error.statusMessage)
                        }
                    }
                }
            }
        }
        favorites = Observer { resource ->
            if (resource != null) {
                when (resource.status) {
                    Resource.Status.SUCCESS -> setFavoritesImage(mViewModel!!.movie.isFavorite)
                    Resource.Status.ERROR -> {
                        val error = resource.error
                        if (error != null) {
                            if (error.statusCode == NETWORK_ERROR_CODE) {
                                showNoConnection(error.statusMessage)
                            } else {
                                showGenericError(error.statusMessage)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.movie_detail_fragment, container, false)
        val intent = requireActivity().intent
        setupFields(view)
        setData()
        mViewModel!!.favorites.observe(viewLifecycleOwner, favorites!!)
        mViewModel!!.getmMovie().observe(viewLifecycleOwner, movie!!)
        mFavorites!!.setOnClickListener { mViewModel!!.saveFavorites(!mViewModel!!.movie.isFavorite) }
        mReviews!!.setOnClickListener {
            if (mMovieFromIntent!!.id != -1) {
                FragmentUtils.replaceFragmentInActivity(requireFragmentManager(),
                        MovieReviewFragment.newInstance(mMovieFromIntent!!.id),
                        R.id.fg_moviedetail,
                        resources.getString(R.string.fg_movie_review_tag),
                        true)
            } else {
                showDialog(NO_DATA_MSG_ERROR_TITLE, NO_REVIEWS_MSG_ERROR_MESSAGE)
            }
        }

        mTrailers!!.setOnClickListener {
            if (mMovieFromIntent!!.id != -1) {
                FragmentUtils.replaceFragmentInActivity(requireFragmentManager(),
                        MovieTrailerFragment.newInstance(mMovieFromIntent!!.id),
                        R.id.fg_moviedetail,
                        resources.getString(R.string.fg_movie_trailer_tag),
                        true)
            } else {
                showDialog(NO_DATA_MSG_ERROR_TITLE, NO_TRAILER_MSG_ERROR_MESSAGE)
            }
        }
        mTryAgainButton!!.setOnClickListener { tryAgain() }
        return view
    }

    private fun setupViewModel() {
        val mMovieRepository = MovieRepository.getInstance(MovieLocalDataSource.getInstance(requireActivity().applicationContext), MovieRemoteDataSource.getInstance())
        mViewModel = ViewModelProviders.of(this,
                MovieDetailFactory(mMovieRepository, mMovieFromIntent!!.id)).get(MovieDetailViewModel::class.java)
    }

    private fun showDialog(title: String, message: String) {
        val noReviewsDialog = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_ok, null)
                .create()

        noReviewsDialog.show()
    }

    private fun setupFields(view: View) {
        mMovieTitle = view.findViewById(R.id.tv_movie_title)
        mMoviePoster = view.findViewById(R.id.iv_movie_poster)
        mMovieReleaseDate = view.findViewById(R.id.tv_movie_release_date)
        mMovieRating = view.findViewById(R.id.tv_movie_rating)
        mMovieOverview = view.findViewById(R.id.tv_movie_overview)
        mReviews = view.findViewById(R.id.tv_movie_reviews_label)
        mTrailers = view.findViewById(R.id.tv_movie_trailers_label)
        mFavorites = view.findViewById(R.id.iv_favorite)
        mNoConnectionGroup = view.findViewById(R.id.group_no_connection)
        mMovieDetailGroup = view.findViewById(R.id.group_movie_detail)
        mNoConnectionText = view.findViewById(R.id.tv_no_conection)
        mTryAgainButton = view.findViewById(R.id.bt_try_again)
        mProgressBar = view.findViewById(R.id.pb_base)
    }

    private fun setData() {
        arguments?.let {
            val args = MovieDetailFragmentArgs.fromBundle(it)
            mMovieFromIntent = args.movie
            setupViewModel()
        }
    }

    private fun showMovieDetails(movie: Movie) {
        mMovieTitle!!.text = if (movie.originalTitle == null)
            ""
        else
            movie.originalTitle
        val imageUrl = if (movie.poster == null)
            ""
        else
            IMAGE_URL + movie.poster
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_photo)
                .into(mMoviePoster)
        mMovieReleaseDate!!.text = if (movie.releaseDate == null)
            "None"
        else
            movie.releaseDate.toString(MOVIE_DATE_PATTERN, Locale.getDefault())
        mMovieRating!!.text = if (movie.voteAverage == null)
            ""
        else
            movie.voteAverage.toString()
        mMovieOverview!!.text = if (movie.overview == null)
            ""
        else
            movie.overview
    }

    private fun setFavoritesImage(isFavorite: Boolean) {
        if (isFavorite) {
            mFavorites!!.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_black_24dp))
        } else {
            mFavorites!!.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_border_black_24dp))
        }
    }

    override fun showLoading() {
        mProgressBar!!.visibility = View.VISIBLE
        mNoConnectionGroup!!.visibility = View.GONE
        mMovieDetailGroup!!.visibility = View.GONE
    }

    override fun hideLoading() {
        mProgressBar!!.visibility = View.GONE
    }

    override fun showResult() {
        mNoConnectionGroup!!.visibility = View.GONE
        mMovieDetailGroup!!.visibility = View.VISIBLE
    }

    override fun showNoConnection(message: String) {
        mMovieDetailGroup!!.visibility = View.GONE
        mNoConnectionText!!.text = message
        mNoConnectionGroup!!.visibility = View.VISIBLE
    }

    override fun showGenericError(message: String) {
        val sortDialog = AlertDialog.Builder(context)
                .setTitle(GENERIC_MSG_ERROR_TITLE)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_ok, null)
                .create()

        sortDialog.show()
    }

    override fun tryAgain() {
        mViewModel!!.tryAgain()
    }

    companion object {

        fun newInstance(): MovieDetailFragment {
            return MovieDetailFragment()
        }
    }
}
