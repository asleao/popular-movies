package br.com.popularmovies.movies.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import br.com.popularmovies.MovieApplication
import br.com.popularmovies.R
import br.com.popularmovies.common.configs.ErrorCodes
import br.com.popularmovies.common.configs.ErrorMessages
import br.com.popularmovies.databinding.FragmentMovieBinding
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.movies.Constants
import br.com.popularmovies.movies.adapters.MovieAdapter
import br.com.popularmovies.movies.adapters.MovieClickListener
import br.com.popularmovies.movies.adapters.MoviePagingAdapter
import br.com.popularmovies.movies.viewmodel.MovieViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class MovieFragment : Fragment(), MovieClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mViewModel: MovieViewModel by viewModels {
        viewModelFactory
    }
    private lateinit var binding: FragmentMovieBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val movieComponent =
            (requireActivity().application as MovieApplication).appComponent.movieComponent()
                .create()
        movieComponent.inject(this)
    }

    private fun setupObservers() {
        setupMoviesPaging()
        setupInTheaterMoviesObserver()
        setupErrorObserver()
    }

    private fun setupInTheaterMoviesObserver() {
        mViewModel.inTheaterMovies.observe(viewLifecycleOwner) { movies ->
            val movie = movies.first()
            Glide.with(requireContext())
                .load(Constants.IMAGE_URL + movie.poster)
                .transform(CenterCrop())
                .error(R.drawable.no_photo)
                .transition(DrawableTransitionOptions.withCrossFade(600))
                .into(binding.ctHeaderImage)
            binding.tvHeaderTitle.text = movie.originalTitle
            val movieAdapter = MovieAdapter(this)
            binding.rvInTheaterMovies.adapter = movieAdapter

            binding.rvTopHatedMovies.adapter = movieAdapter
            movieAdapter.submitList(movies)
        }
    }

    private fun setupMoviesPaging() {
        val pagingAdapter = MoviePagingAdapter(this)
        binding.rvPopularMovies.adapter = pagingAdapter

        pagingAdapter.addLoadStateListener { loadState ->
            binding.rvPopularMovies.isVisible = loadState.mediator?.refresh is LoadState.NotLoading
            binding.iBaseLayout.pbBase.isVisible = loadState.mediator?.refresh is LoadState.Loading
            binding.iBaseLayout.btTryAgain.isVisible =
                loadState.mediator?.refresh is LoadState.Error
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            mViewModel.moviesFlow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupErrorObserver() {
        mViewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                if (error.code == ErrorCodes.NETWORK_ERROR_CODE) {
                    showNoConnection(error.message)
                } else {
                    showGenericError(error.message)
                }
            }
        }
    }

    private fun showNoConnection(message: String) {
        binding.rvPopularMovies.visibility = View.GONE
        binding.rvInTheaterMovies.visibility = View.GONE
        binding.iBaseLayout.tvNoConection.text = message
        binding.iBaseLayout.groupNoConnection.visibility = View.VISIBLE
    }

    private fun showGenericError(message: String) {
        val sortDialog = AlertDialog.Builder(context)
            .setTitle(ErrorMessages.GENERIC_MSG_ERROR_TITLE)
            .setMessage(message)
            .setPositiveButton(R.string.dialog_ok, null)
            .create()

        sortDialog.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        setHasOptionsMenu(true)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mViewModel
        setupObservers()
        return binding.root
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(movie)
        findNavController().navigate(action)
    }
}