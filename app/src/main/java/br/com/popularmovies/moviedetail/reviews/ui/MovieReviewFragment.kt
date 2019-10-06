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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.popularmovies.R
import br.com.popularmovies.base.interfaces.IConection
import br.com.popularmovies.core.network.NETWORK_ERROR_CODE
import br.com.popularmovies.databinding.MovieReviewFragmentBinding
import br.com.popularmovies.moviedetail.reviews.adapters.ReviewAdapter
import br.com.popularmovies.moviedetail.reviews.viewModel.MovieReviewViewModel
import br.com.popularmovies.moviedetail.reviews.viewModel.factories.MovieReviewFactory
import br.com.popularmovies.movies.Constants.GENERIC_MSG_ERROR_TITLE
import br.com.popularmovies.services.movieService.source.MovieRepository
import br.com.popularmovies.services.movieService.source.local.MovieLocalDataSource
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource

class MovieReviewFragment : Fragment(), IConection {

    private lateinit var mViewModel: MovieReviewViewModel
    private lateinit var mReviewsRecyclerView: RecyclerView
    private lateinit var mNoConnectionGroup: Group
    private lateinit var mTryAgainButton: Button
    private lateinit var mNoConnectionText: TextView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mNoReviews: TextView
    private lateinit var binding: MovieReviewFragmentBinding
    private val args by navArgs<MovieReviewFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupObservers()
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
                mMovieRepository?.let { repository ->
                    mViewModel = ViewModelProviders.of(
                        this,
                        MovieReviewFactory(repository, args.movieId)
                    ).get(MovieReviewViewModel::class.java)
                }
            }
        }
    }

    private fun setupObservers() {
        setupMovieReviewObserver()
        setupErrorObserver()
        setupLoadingObserver()
    }

    private fun setupLoadingObserver() {
        mViewModel.loading.observe(this, Observer { status ->
            if (status == true) {
                showLoading()
                mReviewsRecyclerView.visibility = View.GONE
            }
        })
    }

    private fun setupErrorObserver() {
        mViewModel.error.observe(this, Observer { error ->
            if (error != null) {
                if (error.codErro == NETWORK_ERROR_CODE) {
                    showNoConnection(error.message)
                } else {
                    showGenericError(error.message)
                }
            }
        })
    }

    private fun setupMovieReviewObserver() {
        mViewModel.reviews.observe(this, Observer { movieReviews ->
            mReviewsRecyclerView.visibility = View.VISIBLE
            if (movieReviews.reviews.isEmpty()) {
                showNoReviews()
            } else {
                val mReviewAdapter =
                    ReviewAdapter(movieReviews.reviews)
                mReviewsRecyclerView.adapter = mReviewAdapter
                showResult()
            }
        })
    }

    private fun showNoReviews() {
        mNoReviews.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.movie_review_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = mViewModel

        setupFields()
        setupReviewsList()
        mTryAgainButton.setOnClickListener { tryAgain() }

        return binding.root
    }

    private fun setupFields() {
        mNoConnectionGroup = binding.iBaseLayout.groupNoConnection
        mNoConnectionText = binding.iBaseLayout.tvNoConection
        mTryAgainButton = binding.iBaseLayout.btTryAgain
        mProgressBar = binding.iBaseLayout.pbBase
        mNoReviews = binding.tvNoReviews
    }

    private fun setupReviewsList() {
        mReviewsRecyclerView = binding.rvReviews
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
