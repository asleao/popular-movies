package br.com.popularmovies.home.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.viewpager2.widget.MarginPageTransformer
import br.com.popularmovies.common.configs.ErrorMessages
import br.com.popularmovies.feature.home.R
import br.com.popularmovies.feature.home.databinding.FragmentMovieBinding
import br.com.popularmovies.feature.moviedetails.api.MovieDetailsFeatureApi
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
import javax.inject.Provider

class MovieFragment @Inject constructor(
    private val viewModelFactory: Provider<MovieViewModel>,
    private val movieDetailsFeatureApi: MovieDetailsFeatureApi
) : Fragment(),
    MovieClickListener {

    private val viewModel: MovieViewModel by lazy {
        viewModelFactory.get()
    }

    private lateinit var binding: FragmentMovieBinding

    private val pagingNowPlayingMoviesAdapter = MoviePagingAdapter(this)
    private val pagingPopularMoviesAdapter = MoviePagingAdapter(this)
    private val pagingTopHatedMoviesAdapter = MoviePagingAdapter(this)

    private fun setupObservers() {
        setupUiStateObserver()
        val spacingItemDecoration = resources.getDimensionPixelSize(
            R.dimen.list_spacing_default
        )
        setupUiStateObserver()
        setupNewestNowPlayingMoviesObserver()
        setupNowPlayingMoviesFlow(spacingItemDecoration)
        setupPopularMoviesFlow(spacingItemDecoration)
        setupTopHatedMoviesFlow(spacingItemDecoration)
    }

    private fun setupUiStateObserver() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collectLatest { state ->
                when (state) {
                    MovieUiState.Success -> {
                        binding.errorView.isVisible = false
                        binding.container.isVisible = true
                        binding.viewPagerShimmer.setShimmer(null)
                        binding.viewPagerShimmer.background = null
                    }

                    is MovieUiState.Error -> {
                        binding.errorView.isVisible = true
                        binding.container.isVisible = false
                        state.networkError?.let { showError(state.networkError) }
                    }

                    MovieUiState.Loading -> {
                        binding.viewPagerShimmer.showShimmer(true)
                    }
                }
            }
        }
    }

    private fun showError(networkError: Throwable) {
        networkError?.let {
            showNoConnection(networkError.message!!)
        }
//            ?: showGenericError(networkError.message!!)
    }

    private fun setupNewestNowPlayingMoviesObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel
                .randomNowPlayingMovie
                .flowWithLifecycle(lifecycle)
                .collectLatest { movies ->
                    binding.viewPager.adapter =
                        NowPlayingViewPagerAdapter(
                            this@MovieFragment,
                            movies,
                            ::onMovieClick
                        )
                }
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
            val isError = loadState.mediator?.refresh is LoadState.Error
            binding.tvPopularMovies.isVisible = isRefreshSucceded && !isLoading
            binding.rvPopularMovies.isVisible = isRefreshSucceded && !isLoading
            binding.tvPopularMoviesShimmer.showShimmer(isLoading)
            binding.tvPopularMoviesShimmer.isInvisible = !isLoading

            if (!isLoading) {
                binding.tvPopularMoviesShimmer.hideShimmer()
            }

            if (isError) {
                val state = loadState.mediator?.refresh as LoadState.Error
                viewModel.showError(state.error)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.popularMoviesFlow.flowWithLifecycle(lifecycle).collectLatest { pagingData ->
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
            val isError = loadState.mediator?.refresh is LoadState.Error

            binding.tvNowPlayingMovies.isVisible = isRefreshSucceded && !isLoading
            binding.rvNowPlayingMovies.isVisible = isRefreshSucceded && !isLoading
            binding.tvNowPlayingMoviesShimmer.showShimmer(isLoading)
            binding.tvNowPlayingMoviesShimmer.isInvisible = !isLoading
            if (!isLoading) {
                binding.tvNowPlayingMoviesShimmer.hideShimmer()
            }

            if (isError) {
                val state = loadState.mediator?.refresh as LoadState.Error
                viewModel.showError(state.error)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.nowPlayingMoviesFlow.flowWithLifecycle(lifecycle)
                .collectLatest { pagingData ->
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
            val isError = loadState.mediator?.refresh is LoadState.Error
            binding.tvTopRatedMovies.isVisible = isRefreshSucceded && !isLoading
            binding.rvTopRatedMovies.isVisible = isRefreshSucceded && !isLoading
            binding.tvTopRatedMoviesShimmer.showShimmer(isLoading)
            binding.tvTopRatedMoviesShimmer.isInvisible = !isLoading

            if (!isLoading) {
                binding.tvTopRatedMoviesShimmer.hideShimmer()
            }

            //TODO Fix this logic to show error only when there is no data on the adapter
            if (isError) {
                val state = loadState.mediator?.refresh as LoadState.Error
                viewModel.showError(state.error)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.topHatedMoviesFlow.flowWithLifecycle(lifecycle).collectLatest { pagingData ->
                pagingTopHatedMoviesAdapter.submitData(pagingData)
            }
        }

        //      TODO: Try to use flow instead of listener
//        val notLoading = pagingTopHatedMoviesAdapter.loadStateFlow
//            .distinctUntilChangedBy { it.source.refresh }
//            .map { it.source.refresh is LoadState.NotLoading }
//
//        val loading = pagingTopHatedMoviesAdapter.loadStateFlow
//            .distinctUntilChangedBy { it.source.refresh }
//            .map { it.source.refresh is LoadState.Loading }
//
//        val error = pagingTopHatedMoviesAdapter.loadStateFlow
//            .distinctUntilChangedBy { it.source.refresh }
//            .map { it.source.refresh is LoadState.Error }
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            combine(
//                notLoading,
//                loading,
//                error,
//                ::Triple
//            ).distinctUntilChanged()
//                .collectLatest { (notLoading, loading, error) ->
//                    binding.tvTopRatedMovies.isVisible = notLoading
//                    binding.rvTopRatedMovies.isVisible = notLoading
//                    binding.tvTopRatedMoviesShimmer.showShimmer(loading)
//                    binding.tvTopRatedMoviesShimmer.isInvisible = notLoading
//                    if (notLoading) {
//                        binding.tvTopRatedMoviesShimmer.hideShimmer()
//                    }
//
//                    if (error && pagingTopHatedMoviesAdapter.itemCount < 1) {
////                    val state = loadState.mediator?.refresh as LoadState.Error
////                    viewModel.showError(state.error)
//                    }
//                }
//        }
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
        binding.errorView.imageRes =
            br.com.popularmovies.core.ui.R.drawable.ic_cloud_off //TODO Check that
        binding.errorView.buttonText = resources.getString(R.string.try_again)
        binding.errorView.buttonClickListener = {
            pagingNowPlayingMoviesAdapter.retry()
            pagingPopularMoviesAdapter.retry()
            pagingTopHatedMoviesAdapter.retry()
            viewModel.tryAgain()
        }
    }

    override fun onMovieClick(movie: Movie) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(movieDetailsFeatureApi.deeplink(movie.id.toString()))
            .build()
        findNavController().navigate(request)
    }
}