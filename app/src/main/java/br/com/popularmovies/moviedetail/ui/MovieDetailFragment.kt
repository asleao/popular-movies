package br.com.popularmovies.moviedetail.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.popularmovies.R
import br.com.popularmovies.appComponent
import br.com.popularmovies.base.interfaces.IConection
import br.com.popularmovies.core.network.GENERIC_MSG_ERROR_TITLE
import br.com.popularmovies.core.network.NETWORK_ERROR_CODE
import br.com.popularmovies.databinding.MovieDetailFragmentBinding
import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel
import br.com.popularmovies.movies.Constants.IMAGE_URL
import br.com.popularmovies.movies.Constants.MOVIE_DATE_PATTERN
import br.com.popularmovies.services.movieService.response.Movie
import com.squareup.picasso.Picasso
import java.util.*

class MovieDetailFragment : Fragment(), IConection {

    private val args by navArgs<MovieDetailFragmentArgs>()

    private val mViewModel: MovieDetailViewModel by lazy {
        appComponent.movieDetailViewModelFactory.create(args.movie.id)
    }

    private lateinit var binding: MovieDetailFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val movieDetailComponent = appComponent.movieDetailComponent().create()
        movieDetailComponent.inject(this)
    }

    private fun setupLoadingObserver() {
        mViewModel.loading.observe(viewLifecycleOwner, Observer { status ->
            if (status == true) {
                binding.groupMovieDetail.visibility = View.GONE
            } else {
                hideLoading()
            }
        })
    }

    private fun setupErrorObserver() {
        mViewModel.error.observe(viewLifecycleOwner, Observer { error ->
            mViewModel.showLoading(false)
            if (error != null) {
                if (error.codErro == NETWORK_ERROR_CODE) {
                    showMovieDetails(args.movie)
                    setFavoritesImage(args.movie.isFavorite)
                } else {
                    showGenericError(error.message)
                }
            }
        })
    }

    private fun setupObservers() {
        setupLoadingObserver()
        setupErrorObserver()
        setupMovieObserver()
        setupIsMovieFavoriteObserver()
    }

    private fun setupMovieObserver() {
        mViewModel.movie.observe(viewLifecycleOwner, Observer { movie ->
            mViewModel.showLoading(false)
            showMovieDetails(movie)
            setFavoritesImage(movie.isFavorite)
            showResult()
        })
    }

    private fun setupIsMovieFavoriteObserver() {
        mViewModel.isMovieFavorite.observe(viewLifecycleOwner, Observer { isFavorite ->
            mViewModel.showLoading(false)
            setFavoritesImage(isFavorite)
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_detail_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mViewModel
        binding.iBaseLayout.btTryAgain.setOnClickListener { tryAgain() }
        binding.ivFavorite.setOnClickListener {
            mViewModel.updateMovie()
        }
        binding.tvMovieReviewsLabel.setOnClickListener {
            val action = MovieDetailFragmentDirections
                    .actionMovieDetailFragmentToMovieReviewFragment(args.movie.id)
            findNavController().navigate(action)
        }
        binding.tvMovieTrailersLabel.setOnClickListener {
            val action = MovieDetailFragmentDirections
                    .actionMovieDetailFragmentToMovieTrailerFragment(args.movie.id)
            findNavController().navigate(action)
        }
        setupObservers()
        return binding.root
    }

    private fun showMovieDetails(movie: Movie) {
        binding.tvMovieTitle.text = movie.originalTitle
        val imageUrl = IMAGE_URL + movie.poster
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_photo)
                .into(binding.ivMoviePoster)
        binding.tvMovieReleaseDate.text = movie.releaseDate.toString(MOVIE_DATE_PATTERN, Locale.getDefault())
        binding.tvMovieRating.text = movie.voteAverage.toString()
        binding.tvMovieOverview.text = movie.overview
    }

    private fun setFavoritesImage(isFavorite: Boolean) {
        if (isFavorite) {
            binding.ivFavorite.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_favorite_black_24dp
                    )
            )
        } else {
            binding.ivFavorite.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_favorite_border_black_24dp
                    )
            )
        }
    }

    override fun showLoading() {
        binding.iBaseLayout.pbBase.visibility = View.VISIBLE
        binding.iBaseLayout.groupNoConnection.visibility = View.GONE
        binding.groupMovieDetail.visibility = View.GONE
    }

    override fun hideLoading() {
        binding.iBaseLayout.pbBase.visibility = View.GONE
    }

    override fun showResult() {
        binding.iBaseLayout.groupNoConnection.visibility = View.GONE
        binding.groupMovieDetail.visibility = View.VISIBLE
    }

    override fun showNoConnection(message: String) {
        binding.groupMovieDetail.visibility = View.GONE
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
