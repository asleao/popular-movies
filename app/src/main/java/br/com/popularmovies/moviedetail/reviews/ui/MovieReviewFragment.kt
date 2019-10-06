package br.com.popularmovies.moviedetail.reviews.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
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
                binding.rvReviews.visibility = View.GONE
            } else {
                hideLoading()
            }
        })
    }

    private fun setupErrorObserver() {
        mViewModel.error.observe(this, Observer { error ->
            mViewModel.showLoading(false)
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
            mViewModel.showLoading(false)
            binding.rvReviews.visibility = View.VISIBLE
            if (movieReviews.reviews.isEmpty()) {
                showNoReviews()
            } else {
                val mReviewAdapter =
                    ReviewAdapter(movieReviews.reviews)
                binding.rvReviews.adapter = mReviewAdapter
                showResult()
            }
        })
    }

    private fun showNoReviews() {
        binding.tvNoReviews.visibility = View.VISIBLE
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
        setupReviewsList()
        binding.iBaseLayout.btTryAgain.setOnClickListener { tryAgain() }

        return binding.root
    }

    private fun setupReviewsList() {
        binding.rvReviews.layoutManager = LinearLayoutManager(context)
    }

    override fun showLoading() {
        binding.iBaseLayout.pbBase.visibility = View.VISIBLE
        binding.iBaseLayout.groupNoConnection.visibility = View.GONE
    }

    override fun hideLoading() {
        binding.iBaseLayout.pbBase.visibility = View.GONE
    }

    override fun showResult() {
        binding.iBaseLayout.groupNoConnection.visibility = View.GONE
        binding.tvNoReviews.visibility = View.GONE
    }

    override fun showNoConnection(message: String) {
        binding.rvReviews.visibility = View.GONE
        binding.iBaseLayout.tvNoConection.text = message
        binding.iBaseLayout.groupNoConnection.visibility = View.VISIBLE
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
