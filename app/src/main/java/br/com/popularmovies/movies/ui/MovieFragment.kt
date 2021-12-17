package br.com.popularmovies.movies.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import br.com.popularmovies.MovieApplication
import br.com.popularmovies.R
import br.com.popularmovies.databinding.FragmentMovieBinding
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieOrderType
import br.com.popularmovies.movies.Constants.*
import br.com.popularmovies.movies.adapters.MovieAdapter
import br.com.popularmovies.movies.adapters.MovieClickListener
import br.com.popularmovies.movies.adapters.MoviePagingAdapter
import br.com.popularmovies.movies.viewmodel.MovieViewModel
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
        // setupMoviesObserver()
        setupMoviesPaging()
//        setupLoadingObserver()
//        setupErrorObserver()
    }

    private fun setupMoviesPaging() {
        val pagingAdapter = MoviePagingAdapter(this)
        binding.rvMovies.adapter = pagingAdapter

        pagingAdapter.addLoadStateListener { loadState ->
            binding.rvMovies.isVisible = loadState.mediator?.refresh is LoadState.NotLoading
            binding.iBaseLayout.pbBase.isVisible = loadState.mediator?.refresh is LoadState.Loading
            binding.iBaseLayout.btTryAgain.isVisible =
                loadState.mediator?.refresh is LoadState.Error
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            mViewModel.moviesFlow.collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }

    private fun setupMoviesObserver() {
        mViewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            mViewModel.showLoading(false)
            val mMovieAdapter =
                MovieAdapter(this)
            mMovieAdapter.swapData(movies)
            binding.rvMovies.adapter = mMovieAdapter

//            showResult()
            mViewModel.cleanError()
        })
    }

//    private fun setupLoadingObserver() {
//        mViewModel.loading.observe(viewLifecycleOwner, Observer { status ->
//            if (status == true) {
//                binding.rvMovies.visibility = View.GONE
//            } else {
//                hideLoading()
//            }
//        })
//    }
//
//    private fun setupErrorObserver() {
//        mViewModel.error.observe(viewLifecycleOwner, Observer { error ->
//            mViewModel.showLoading(false)
//            if (error != null) {
//                if (error.code == NETWORK_ERROR_CODE) {
//                    showNoConnection(error.message)
//                    tryAgain()
//                } else {
//                    showGenericError(error.message)
//                }
//            }
//        })
//    }

//    private fun hideLoading() {
//        binding.iBaseLayout.pbBase.visibility = View.GONE
//    }
//
//    private fun showResult() {
//        binding.iBaseLayout.groupNoConnection.visibility = View.GONE
//        binding.rvMovies.visibility = View.VISIBLE
//    }
//
//    private fun showNoConnection(message: String) {
//        binding.rvMovies.visibility = View.GONE
//        binding.iBaseLayout.tvNoConection.text = message
//        binding.iBaseLayout.groupNoConnection.visibility = View.VISIBLE
//    }
//
//    private fun showGenericError(message: String) {
//        val sortDialog = AlertDialog.Builder(context)
//            .setTitle(GENERIC_MSG_ERROR_TITLE)
//            .setMessage(message)
//            .setPositiveButton(R.string.dialog_ok, null)
//            .create()
//
//        sortDialog.show()
//    }
//
//    private fun tryAgain() {
//        binding.iBaseLayout.btTryAgain.setOnClickListener { mViewModel.tryAgain() }
//    }

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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sort, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.m_sort) {
            val values = arrayOf<CharSequence>("Most Popular", "Top Rated", "Favorites")
            val sortDialog = AlertDialog.Builder(context)
                .setTitle(TITLE_DIALOG_FILTER)
                .setSingleChoiceItems(values, mViewModel.selectedFilterIndex) { dialog, which ->
                    changeSortOrder(which)
                    dialog.dismiss()
                }
                .create()

            sortDialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeSortOrder(item: Int) {
        when (item) {
            INDEX_FILTER_MOST_POPULAR -> {
                mViewModel.setMovieOrder(MovieOrderType.MostPopular)
                mViewModel.selectedFilterIndex = 0
            }
            INDEX_FILTER_TOP_RATED -> {
                mViewModel.setMovieOrder(MovieOrderType.TopHated)
                mViewModel.selectedFilterIndex = 1
            }
            INDEX_FILTER_FAVORITES -> {
                mViewModel.setMovieOrder(MovieOrderType.Favorites)
                mViewModel.selectedFilterIndex = 2
            }
        }
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(movie)
        findNavController().navigate(action)
    }
}