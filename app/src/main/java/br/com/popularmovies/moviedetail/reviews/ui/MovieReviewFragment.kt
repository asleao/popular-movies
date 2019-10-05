package br.com.popularmovies.moviedetail.reviews.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.popularmovies.R
import br.com.popularmovies.base.interfaces.IConection
import br.com.popularmovies.data.Constants.NETWORK_ERROR_CODE
import br.com.popularmovies.data.model.OldResource
import br.com.popularmovies.moviedetail.reviews.adapters.ReviewAdapter
import br.com.popularmovies.moviedetail.reviews.viewModel.MovieReviewViewModel
import br.com.popularmovies.moviedetail.reviews.viewModel.factories.MovieReviewFactory
import br.com.popularmovies.movies.Constants.GENERIC_MSG_ERROR_TITLE
import br.com.popularmovies.services.movieService.response.MovieReviews
import br.com.popularmovies.services.movieService.source.MovieRepository
import br.com.popularmovies.services.movieService.source.local.MovieLocalDataSource
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource

class MovieReviewFragment : Fragment(), IConection {

    private lateinit var mViewModel: MovieReviewViewModel
    private lateinit var mReviewsRecyclerView: RecyclerView
    private lateinit var reviewsObserver: Observer<OldResource<MovieReviews>>
    private lateinit var mNoConnectionGroup: Group
    private lateinit var mTryAgainButton: Button
    private lateinit var mNoConnectionText: TextView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mNoReviews: TextView
    private val args by navArgs<MovieReviewFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        reviewsObserver = Observer { movieReviewsResource ->
            if (movieReviewsResource != null)
                when (movieReviewsResource.status) {
                    OldResource.Status.LOADING -> {
                        showLoading()
                        mReviewsRecyclerView.visibility = View.GONE
                    }
                    OldResource.Status.SUCCESS -> {
                        hideLoading()
                        mReviewsRecyclerView.visibility = View.VISIBLE
                        if (movieReviewsResource.data != null) {
                            if (movieReviewsResource.data.reviews.isEmpty()) {
                                showNoReviews()
                            } else {
                                val mReviewAdapter =
                                    ReviewAdapter(movieReviewsResource.data.reviews)
                                mReviewsRecyclerView.adapter = mReviewAdapter
                                showResult()
                            }
                        }
                    }
                    OldResource.Status.ERROR -> {
                        hideLoading()
                        val error = movieReviewsResource.error
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

    private fun showNoReviews() {
        mNoReviews.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.movie_review_fragment, container, false)
        val movieId = args.movieId
        val mMovieRepository = MovieRepository.getInstance(
            MovieLocalDataSource.getInstance(requireActivity().applicationContext),
            MovieRemoteDataSource.getInstance()
        )

        mMovieRepository?.let { repository ->
            mViewModel = ViewModelProviders.of(
                this,
                MovieReviewFactory(repository, movieId)
            ).get(MovieReviewViewModel::class.java)
        }

        setupFields(view)
        setupReviewsList(view)
        mViewModel.reviews.observe(viewLifecycleOwner, reviewsObserver)
        mTryAgainButton.setOnClickListener { tryAgain() }
        return view
    }

    private fun setupFields(view: View) {
        mNoConnectionGroup = view.findViewById(R.id.group_no_connection)
        mNoConnectionText = view.findViewById(R.id.tv_no_conection)
        mTryAgainButton = view.findViewById(R.id.bt_try_again)
        mProgressBar = view.findViewById(R.id.pb_base)
        mNoReviews = view.findViewById(R.id.tv_no_reviews)
    }

    private fun setupReviewsList(view: View) {
        mReviewsRecyclerView = view.findViewById(R.id.rv_reviews)
        mReviewsRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun showLoading() {
        mProgressBar.visibility = View.VISIBLE
        mNoConnectionGroup.visibility = View.GONE
    }

    override fun hideLoading() {
        mProgressBar.visibility = View.GONE
    }

    override fun showResult() {
        mNoConnectionGroup.visibility = View.GONE
        mNoReviews.visibility = View.GONE
    }

    override fun showNoConnection(message: String) {
        mReviewsRecyclerView.visibility = View.GONE
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
}
