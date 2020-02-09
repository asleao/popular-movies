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
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.popularmovies.R
import br.com.popularmovies.appComponent
import br.com.popularmovies.base.interfaces.IConection
import br.com.popularmovies.core.network.GENERIC_MSG_ERROR_TITLE
import br.com.popularmovies.core.network.NETWORK_ERROR_CODE
import br.com.popularmovies.core.network.local.AppDatabase
import br.com.popularmovies.data.model.OldResource
import br.com.popularmovies.moviedetail.trailers.Constants.YOUTUBE_URL
import br.com.popularmovies.moviedetail.trailers.adapters.TrailerAdapter
import br.com.popularmovies.moviedetail.trailers.adapters.TrailerClickListener
import br.com.popularmovies.moviedetail.trailers.viewmodel.MovieTrailerViewModel
import br.com.popularmovies.services.movieService.response.MovieTrailers
import javax.inject.Inject

class MovieTrailerFragment : Fragment(), IConection, TrailerClickListener {

    private val args by navArgs<MovieTrailerFragmentArgs>()

    private val mViewModel: MovieTrailerViewModel by lazy {
        appComponent.movieTrailerViewModelFactory.create(args.movieId)
    }
    private lateinit var mTrailersRecyclerView: RecyclerView
    private lateinit var trailersObserver: Observer<OldResource<MovieTrailers>>
    private lateinit var mNoConnectionGroup: Group
    private lateinit var mTryAgainButton: Button
    private lateinit var mNoConnectionText: TextView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mNoTrailers: TextView
    @Inject
    lateinit var appDatabase: AppDatabase


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
        trailersObserver = Observer { movieReviewsResource ->
            if (movieReviewsResource != null)
                when (movieReviewsResource.status) {
                    OldResource.Status.LOADING -> {
                        showLoading()
                        mTrailersRecyclerView.visibility = View.GONE
                    }
                    OldResource.Status.SUCCESS -> {
                        hideLoading()
                        mTrailersRecyclerView.visibility = View.VISIBLE
                        if (movieReviewsResource.data != null) {
                            if (movieReviewsResource.data.trailers.isEmpty()) {
                                showNoTrailers()
                            } else {
                                val mTrailerAdapter = TrailerAdapter(
                                        movieReviewsResource.data.trailers,
                                        this@MovieTrailerFragment
                                )
                                mTrailersRecyclerView.adapter = mTrailerAdapter
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

    private fun showNoTrailers() {
        mNoTrailers.visibility = View.VISIBLE
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.movie_trailer_fragment, container, false)
        setupFields(view)
        setupTrailersList(view)
        mViewModel.trailers.observe(viewLifecycleOwner, trailersObserver)
        mTryAgainButton.setOnClickListener { tryAgain() }
        return view
    }

    private fun setupFields(view: View) {
        mNoConnectionGroup = view.findViewById(R.id.group_no_connection)
        mNoConnectionText = view.findViewById(R.id.tv_no_conection)
        mTryAgainButton = view.findViewById(R.id.bt_try_again)
        mProgressBar = view.findViewById(R.id.pb_base)
        mNoTrailers = view.findViewById(R.id.tv_no_trailers)
    }

    private fun setupTrailersList(view: View) {
        mTrailersRecyclerView = view.findViewById(R.id.rv_trailers)
        mTrailersRecyclerView.layoutManager = LinearLayoutManager(context)
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
        mNoTrailers.visibility = View.GONE
    }

    override fun showNoConnection(message: String) {
        mTrailersRecyclerView.visibility = View.GONE
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
