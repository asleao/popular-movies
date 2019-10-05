package br.com.popularmovies.moviedetail.ui

import android.app.AlertDialog
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
import androidx.navigation.fragment.findNavController
import br.com.popularmovies.R
import br.com.popularmovies.base.interfaces.IConection
import br.com.popularmovies.core.network.CODE_ERRO_PADRAO
import br.com.popularmovies.core.network.NETWORK_ERROR_CODE
import br.com.popularmovies.data.model.OldResource
import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel
import br.com.popularmovies.moviedetail.viewmodel.factories.MovieDetailFactory
import br.com.popularmovies.movies.Constants.GENERIC_MSG_ERROR_TITLE
import br.com.popularmovies.movies.Constants.IMAGE_URL
import br.com.popularmovies.movies.Constants.MOVIE_DATE_PATTERN
import br.com.popularmovies.movies.Constants.NO_DATA_MSG_ERROR_TITLE
import br.com.popularmovies.movies.Constants.NO_REVIEWS_MSG_ERROR_MESSAGE
import br.com.popularmovies.movies.Constants.NO_TRAILER_MSG_ERROR_MESSAGE
import br.com.popularmovies.services.movieService.response.Movie
import br.com.popularmovies.services.movieService.source.MovieRepository
import br.com.popularmovies.services.movieService.source.local.MovieLocalDataSource
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource
import com.squareup.picasso.Picasso
import java.util.Locale

class MovieDetailFragment : Fragment(), IConection {

    private lateinit var mViewModel: MovieDetailViewModel
    private lateinit var mMovieFromIntent: Movie
    private lateinit var mMovieTitle: TextView
    private lateinit var mMoviePoster: ImageView
    private lateinit var mMovieReleaseDate: TextView
    private lateinit var mMovieRating: TextView
    private lateinit var mMovieOverview: TextView
    private lateinit var mReviews: TextView
    private lateinit var mTrailers: TextView
    private lateinit var mFavorites: AppCompatImageView
    private lateinit var favorites: Observer<OldResource<Void>>
    private lateinit var movie: Observer<OldResource<Movie>>
    private lateinit var mNoConnectionGroup: Group
    private lateinit var mMovieDetailGroup: Group
    private lateinit var mTryAgainButton: Button
    private lateinit var mNoConnectionText: TextView
    private lateinit var mProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        movie = Observer { movieResource ->
            when (movieResource.status) {
                OldResource.Status.LOADING -> showLoading()
                OldResource.Status.SUCCESS -> {
                    hideLoading()
                    if (movieResource.data != null) {
                        showMovieDetails(movieResource.data)
                        mViewModel.movie = movieResource.data
                        setFavoritesImage(movieResource.data.isFavorite)
                    }
                    showResult()
                }
                OldResource.Status.ERROR -> {
                    hideLoading()
                    val error = movieResource.error
                    if (error != null) {
                        if (error.statusCode == NETWORK_ERROR_CODE) {
                            mViewModel.movie = mMovieFromIntent
                            showMovieDetails(mMovieFromIntent)
                            setFavoritesImage(mMovieFromIntent.isFavorite)
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
                    OldResource.Status.SUCCESS -> setFavoritesImage(mViewModel.movie.isFavorite)
                    OldResource.Status.ERROR -> {
                        val error = resource.error
                        if (error != null) {
                            if (error.statusCode == CODE_ERRO_PADRAO) {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_detail_fragment, container, false)
        setupFields(view)
        setData()
        mViewModel.favorites.observe(viewLifecycleOwner, favorites)
        mViewModel.getmMovie().observe(viewLifecycleOwner, movie)
        mFavorites.setOnClickListener { mViewModel.saveFavorites(!mViewModel.movie.isFavorite) }
        mReviews.setOnClickListener {
            if (mMovieFromIntent.id != -1) {
                val action =
                    MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieReviewFragment(
                        mMovieFromIntent.id
                    )
                findNavController().navigate(action)
            } else {
                showDialog(NO_DATA_MSG_ERROR_TITLE, NO_REVIEWS_MSG_ERROR_MESSAGE)
            }
        }

        mTrailers.setOnClickListener {
            if (mMovieFromIntent.id != -1) {
                val action =
                    MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieTrailerFragment(
                        mMovieFromIntent.id
                    )
                findNavController().navigate(action)
            } else {
                showDialog(NO_DATA_MSG_ERROR_TITLE, NO_TRAILER_MSG_ERROR_MESSAGE)
            }
        }
        mTryAgainButton.setOnClickListener { tryAgain() }
        return view
    }

    private fun setupViewModel() {
        val mMovieLocalDataSource =
            MovieLocalDataSource.getInstance(requireActivity().applicationContext)
        mMovieLocalDataSource?.let {
            MovieRemoteDataSource.instance?.let { mMovieRemoteDataSource ->
                val mMovieRepository = MovieRepository.getInstance(
                    mMovieLocalDataSource,
                    mMovieRemoteDataSource
                )
                mViewModel = ViewModelProviders.of(
                    this,
                    MovieDetailFactory(mMovieRepository, mMovieFromIntent.id)
                ).get(MovieDetailViewModel::class.java)
            }

        }
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
        mMovieTitle.text = if (movie.originalTitle == null)
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
        mMovieReleaseDate.text = if (movie.releaseDate == null)
            "None"
        else
            movie.releaseDate.toString(MOVIE_DATE_PATTERN, Locale.getDefault())
        mMovieRating.text = if (movie.voteAverage == null)
            ""
        else
            movie.voteAverage.toString()
        mMovieOverview.text = if (movie.overview == null)
            ""
        else
            movie.overview
    }

    private fun setFavoritesImage(isFavorite: Boolean) {
        if (isFavorite) {
            mFavorites.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_favorite_black_24dp
                )
            )
        } else {
            mFavorites.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_favorite_border_black_24dp
                )
            )
        }
    }

    override fun showLoading() {
        mProgressBar.visibility = View.VISIBLE
        mNoConnectionGroup.visibility = View.GONE
        mMovieDetailGroup.visibility = View.GONE
    }

    override fun hideLoading() {
        mProgressBar.visibility = View.GONE
    }

    override fun showResult() {
        mNoConnectionGroup.visibility = View.GONE
        mMovieDetailGroup.visibility = View.VISIBLE
    }

    override fun showNoConnection(message: String) {
        mMovieDetailGroup.visibility = View.GONE
        mNoConnectionText.text = message
        mNoConnectionGroup.visibility = View.VISIBLE
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
        mViewModel.tryAgain()
    }

    companion object {

        fun newInstance(): MovieDetailFragment {
            return MovieDetailFragment()
        }
    }
}
