package br.com.popularmovies.movies.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.viewpager2.widget.MarginPageTransformer
import br.com.popularmovies.MovieApplication
import br.com.popularmovies.R
import br.com.popularmovies.common.configs.ErrorCodes
import br.com.popularmovies.common.configs.ErrorMessages
import br.com.popularmovies.databinding.FragmentMovieBinding
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.movies.adapters.MovieClickListener
import br.com.popularmovies.movies.adapters.MoviePagingAdapter
import br.com.popularmovies.movies.adapters.NowPlayingViewPagerAdapter
import br.com.popularmovies.movies.viewmodel.MovieViewModel
import br.com.popularmovies.utils.SpacingItemDecoration
import br.com.popularmovies.utils.SpacingItemDecorationType
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
        val spacingItemDecoration = resources.getDimensionPixelSize(
            R.dimen.list_spacing_default
        )
        setupNewestNowPlayingMovieObserver()
        setupNowPlayingMoviesFlow(spacingItemDecoration)
        setupPopularMoviesFlow(spacingItemDecoration)
        setupTopHatedMoviesFlow(spacingItemDecoration)
        setupErrorObserver()
    }

    private fun setupNewestNowPlayingMovieObserver() {
        mViewModel.randomNowPlayingMovie.observe(viewLifecycleOwner) { movies ->
            binding.viewPagerShimmer.setShimmer(null)
            binding.viewPagerShimmer.background = null
            binding.viewPager.adapter = NowPlayingViewPagerAdapter(this, movies)
        }
    }

    private fun setupPopularMoviesFlow(spacingItemDecoration: Int) {
        val pagingAdapter = MoviePagingAdapter(this)
        binding.rvPopularMovies.adapter = pagingAdapter
        binding.rvPopularMovies.addItemDecoration(
            SpacingItemDecoration(
                SpacingItemDecorationType.Horizontal(spacingItemDecoration)
            )
        )
        pagingAdapter.addLoadStateListener { loadState ->
            val isRefreshSucceded =
                loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
            val isLoading = loadState.mediator?.refresh is LoadState.Loading
            binding.tvPopularMovies.isVisible = isRefreshSucceded && !isLoading
            binding.rvPopularMovies.isVisible = isRefreshSucceded && !isLoading
            binding.tvPopularMoviesShimmer.showShimmer(isLoading)
            binding.tvPopularMoviesShimmer.isInvisible = !isLoading

            if (!isLoading) {
                binding.tvPopularMoviesShimmer.hideShimmer()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            mViewModel.popularMoviesFlow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupNowPlayingMoviesFlow(spacingItemDecoration: Int) {
        val pagingAdapter = MoviePagingAdapter(this)
        binding.rvNowPlayingMovies.adapter = pagingAdapter
        binding.rvNowPlayingMovies.addItemDecoration(
            SpacingItemDecoration(
                SpacingItemDecorationType.Horizontal(spacingItemDecoration)
            )
        )

        pagingAdapter.addLoadStateListener { loadState ->
            val isRefreshSucceded =
                loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
            val isLoading = loadState.mediator?.refresh is LoadState.Loading
            binding.tvNowPlayingMovies.isVisible = isRefreshSucceded && !isLoading
            binding.rvNowPlayingMovies.isVisible = isRefreshSucceded && !isLoading
            binding.tvNowPlayingMoviesShimmer.showShimmer(isLoading)
            binding.tvNowPlayingMoviesShimmer.isInvisible = !isLoading
            if (!isLoading) {
                binding.tvNowPlayingMoviesShimmer.hideShimmer()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            mViewModel.nowPlayingMoviesFlow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupTopHatedMoviesFlow(spacingItemDecoration: Int) {
        val pagingAdapter = MoviePagingAdapter(this)
        binding.rvTopRatedMovies.adapter = pagingAdapter
        binding.rvTopRatedMovies.addItemDecoration(
            SpacingItemDecoration(
                SpacingItemDecorationType.Horizontal(spacingItemDecoration)
            )
        )

        pagingAdapter.addLoadStateListener { loadState ->
            val isRefreshSucceded =
                loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
            val isLoading = loadState.mediator?.refresh is LoadState.Loading
            binding.tvTopRatedMovies.isVisible = isRefreshSucceded && !isLoading
            binding.rvTopRatedMovies.isVisible = isRefreshSucceded && !isLoading
            binding.tvTopRatedMoviesShimmer.showShimmer(isLoading)
            binding.tvTopRatedMoviesShimmer.isInvisible = !isLoading

            if (!isLoading) {
                binding.tvTopRatedMoviesShimmer.hideShimmer()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            mViewModel.topHatedMoviesFlow.collectLatest { pagingData ->
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
        binding.appBar.isVisible = false
        binding.container.isVisible = false
        binding.errorView.isVisible = true
        binding.errorView.description = message
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
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mViewModel
        setupObservers()
        binding.viewPager.setPageTransformer(MarginPageTransformer(44))//TODO add dimens here
        setupErrorView()
        return binding.root
    }

    private fun setupErrorView() {
        binding.errorView.imageRes = R.drawable.ic_cloud_off
        binding.errorView.buttonText = "Try again?"
        binding.errorView.buttonClickListener = {
            binding.viewModel?.tryAgain()
        }
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(movie)
        findNavController().navigate(action)
    }
}