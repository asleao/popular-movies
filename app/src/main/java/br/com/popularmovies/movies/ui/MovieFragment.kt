package br.com.popularmovies.movies.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat.HORIZONTAL
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.popularmovies.MovieApplication
import br.com.popularmovies.R
import br.com.popularmovies.databinding.FragmentMovieBinding
import br.com.popularmovies.entities.movie.Movie
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
        setupMoviesPaging()
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