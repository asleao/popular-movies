package br.com.popularmovies.moviedetail.trailers.ui

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import br.com.popularmovies.R
import br.com.popularmovies.appComponent
import br.com.popularmovies.base.interfaces.IConection
import br.com.popularmovies.common.configs.ErrorCodes.NETWORK_ERROR_CODE
import br.com.popularmovies.common.configs.ErrorMessages.GENERIC_MSG_ERROR_TITLE
import br.com.popularmovies.databinding.MovieTrailerFragmentBinding
import br.com.popularmovies.moviedetail.trailers.Constants.YOUTUBE_URL
import br.com.popularmovies.moviedetail.trailers.adapters.TrailerAdapter
import br.com.popularmovies.moviedetail.trailers.adapters.TrailerClickListener
import br.com.popularmovies.moviedetail.trailers.viewmodel.MovieTrailerViewModel
import kotlinx.android.synthetic.main.movie_trailer_fragment.*

class MovieTrailerFragment : Fragment(), IConection, TrailerClickListener {

    private val args by navArgs<MovieTrailerFragmentArgs>()

    private val mViewModel: MovieTrailerViewModel by lazy {
        appComponent.movieTrailerViewModelFactory.create(args.movieId)
    }

    private lateinit var binding: MovieTrailerFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val movieDetailComponent = appComponent.movieDetailComponent().create()
        movieDetailComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        setupMovieTrailersObserver()
        setupErrorObserver()
        setupLoadingObserver()
    }

    private fun setupLoadingObserver() {
        mViewModel.loading.observe(this, Observer { status ->
            if (status == true) {
                binding.rvTrailers.visibility = View.GONE
            } else {
                hideLoading()
            }
        })
    }

    private fun setupErrorObserver() {
        mViewModel.error.observe(this, Observer { error ->
            mViewModel.showLoading(false)
            if (error != null) {
                if (error.code == NETWORK_ERROR_CODE) {
                    showNoConnection(error.message)
                } else {
                    showGenericError(error.message)
                }
            }
        })
    }

    private fun setupMovieTrailersObserver() {
        mViewModel.trailers.observe(this, Observer { movieTrailers ->
            mViewModel.showLoading(false)
            if (movieTrailers.isEmpty()) {
                showNoTrailers()
            } else {
                binding.rvTrailers.adapter = TrailerAdapter(
                        movieTrailers,
                        this@MovieTrailerFragment
                )
                showResult()
            }
        })
    }

    private fun showNoTrailers() {
        binding.tvNoTrailers.visibility = View.VISIBLE
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_trailer_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = mViewModel
        binding.iBaseLayout.btTryAgain.setOnClickListener { tryAgain() }
        return binding.root
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
        binding.tvNoTrailers.visibility = View.GONE
        binding.rvTrailers.visibility = View.VISIBLE
    }

    override fun showNoConnection(message: String) {
        binding.rvTrailers.visibility = View.GONE
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

    override fun onPlay(videoKey: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoKey"))
        val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(YOUTUBE_URL + videoKey)
        )
        try {
            requireContext().startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            requireContext().startActivity(webIntent)
        }
    }

    override fun onShare(videoUrl: String) {
        val mimeType = "text/plain"

        val title = "Sharing this trailer on"

        ShareCompat.IntentBuilder
                .from(requireActivity())
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(videoUrl)
                .startChooser()
    }
}
