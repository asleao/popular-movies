package br.com.popularmovies.home.ui

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.viewpager2.widget.MarginPageTransformer
import br.com.popularmovies.common.configs.ErrorMessages
import br.com.popularmovies.common.models.base.NetworkError
import br.com.popularmovies.feature.home.R
import br.com.popularmovies.feature.home.databinding.FragmentMovieBinding
import br.com.popularmovies.home.adapters.MovieClickListener
import br.com.popularmovies.home.adapters.MoviePagingAdapter
import br.com.popularmovies.home.adapters.NowPlayingViewPagerAdapter
import br.com.popularmovies.home.utils.SpacingItemDecoration
import br.com.popularmovies.home.utils.SpacingItemDecorationType
import br.com.popularmovies.home.viewmodel.MovieUiState
import br.com.popularmovies.home.viewmodel.MovieViewModel
import br.com.popularmovies.model.movie.Movie
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieFragment : Fragment(), MovieClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MovieViewModel by viewModels {
        viewModelFactory
    }
    private lateinit var binding: FragmentMovieBinding

    private val pagingNowPlayingMoviesAdapter = MoviePagingAdapter(this)
    private val pagingPopularMoviesAdapter = MoviePagingAdapter(this)
    private val pagingTopHatedMoviesAdapter = MoviePagingAdapter(this)


    override fun onAttach(context: Context) {
        super.onAttach(context)
//        val movieComponent =
//            (requireActivity().application as MovieApplication).appComponent.movieComponent()
//                .create()
//        movieComponent.inject(this)
    }

    private fun setupObservers() {
        val spacingItemDecoration = resources.getDimensionPixelSize(
            R.dimen.list_spacing_default
        )
        setupUiStateObserver()
        setupNewestNowPlayingMovieObserver()
        setupNowPlayingMoviesFlow(spacingItemDecoration)
        setupPopularMoviesFlow(spacingItemDecoration)
        setupTopHatedMoviesFlow(spacingItemDecoration)
    }

    private fun setupUiStateObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    when (state) {
                        MovieUiState.Success -> {
                            binding.errorView.isVisible = false
                            binding.container.isVisible = true
                        }

                        is MovieUiState.Error -> {
                            binding.errorView.isVisible = true
                            binding.container.isVisible = false
                            showError(state.networkError)
                        }
                    }
                }
            }
        }
    }

    private fun showError(networkError: NetworkError?) {
        networkError?.let {
            showNoConnection(networkError.message)
        } ?: showGenericError(networkError!!.message)
    }

    private fun setupNewestNowPlayingMovieObserver() {
        viewModel.randomNowPlayingMovie.observe(viewLifecycleOwner) { movies ->
            binding.viewPagerShimmer.setShimmer(null)
            binding.viewPagerShimmer.background = null
            binding.viewPager.adapter = NowPlayingViewPagerAdapter(this, movies, ::onMovieClick)
        }
    }

    private fun setupPopularMoviesFlow(spacingItemDecoration: Int) {
        binding.rvPopularMovies.adapter = pagingPopularMoviesAdapter
        binding.rvPopularMovies.addItemDecoration(
            SpacingItemDecoration(
                SpacingItemDecorationType.Horizontal(spacingItemDecoration)
            )
        )
        pagingPopularMoviesAdapter.addLoadStateListener { loadState ->
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
            viewModel.popularMoviesFlow.collectLatest { pagingData ->
                pagingPopularMoviesAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupNowPlayingMoviesFlow(spacingItemDecoration: Int) {
        binding.rvNowPlayingMovies.adapter = pagingNowPlayingMoviesAdapter
        binding.rvNowPlayingMovies.addItemDecoration(
            SpacingItemDecoration(
                SpacingItemDecorationType.Horizontal(spacingItemDecoration)
            )
        )

        pagingNowPlayingMoviesAdapter.addLoadStateListener { loadState ->
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
            viewModel.nowPlayingMoviesFlow.collectLatest { pagingData ->
                pagingNowPlayingMoviesAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupTopHatedMoviesFlow(spacingItemDecoration: Int) {
        binding.rvTopRatedMovies.adapter = pagingTopHatedMoviesAdapter
        binding.rvTopRatedMovies.addItemDecoration(
            SpacingItemDecoration(
                SpacingItemDecorationType.Horizontal(spacingItemDecoration)
            )
        )

        pagingTopHatedMoviesAdapter.addLoadStateListener { loadState ->
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
            viewModel.topHatedMoviesFlow.collectLatest { pagingData ->
                pagingTopHatedMoviesAdapter.submitData(pagingData)
            }
        }
    }

    private fun showNoConnection(message: String) {
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
        binding.viewModel = viewModel
        setupObservers()
        binding.viewPager.setPageTransformer(MarginPageTransformer(44))//TODO add dimens here
        setupErrorView()
        return binding.root
    }

    private fun setupErrorView() {
        binding.errorView.imageRes = br.com.popularmovies.core.ui.R.drawable.ic_cloud_off //TODO Check that
        binding.errorView.buttonText = resources.getString(R.string.try_again)
        binding.errorView.buttonClickListener = {
            pagingNowPlayingMoviesAdapter.retry()
            pagingPopularMoviesAdapter.retry()
            pagingTopHatedMoviesAdapter.retry()
            viewModel.tryAgain()
        }
    }

    override fun onMovieClick(movie: Movie) {
//        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(movie.id)
//        findNavController().navigate(action)
    }
}