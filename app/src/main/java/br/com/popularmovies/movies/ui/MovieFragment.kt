package br.com.popularmovies.movies.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.popularmovies.MovieApplication
import br.com.popularmovies.R
import br.com.popularmovies.core.network.GENERIC_MSG_ERROR_TITLE
import br.com.popularmovies.core.network.NETWORK_ERROR_CODE
import br.com.popularmovies.databinding.FragmentMovieBinding
import br.com.popularmovies.movies.Constants.*
import br.com.popularmovies.movies.adapters.MovieAdapter
import br.com.popularmovies.movies.adapters.MovieClickListener
import br.com.popularmovies.movies.viewmodel.MovieViewModel
import br.com.popularmovies.services.movieService.response.Movie
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
        val movieComponent = (requireActivity().application as MovieApplication).appComponent.movieComponent().create()
        movieComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setHasOptionsMenu(true)
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.movies.value?.movies?.let { movies ->
            val mMovieAdapter =
                    MovieAdapter(movies, this)
            binding.rvMovies.adapter = mMovieAdapter
            showResult()
        }
    }

    private fun setupObservers() {
        setupMoviesObserver()
        setupLoadingObserver()
        setupErrorObserver()
    }

    private fun setupMoviesObserver() {
        mViewModel.movies.observe(this, Observer { moviesResource ->
            mViewModel.showLoading(false)
            val mMovieAdapter =
                    MovieAdapter(moviesResource.movies, this)
            binding.rvMovies.adapter = mMovieAdapter
            showResult()
        })
    }

    private fun setupLoadingObserver() {
        mViewModel.loading.observe(this, Observer { status ->
            if (status == true) {
                showLoading()
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
                    tryAgain()
                } else {
                    showGenericError(error.message)
                }
            }
        })
    }

    private fun hideLoading() {
        binding.iBaseLayout.pbBase.visibility = View.GONE
    }

    private fun showLoading() {
        binding.iBaseLayout.pbBase.visibility = View.VISIBLE
        binding.rvMovies.visibility = View.GONE
        binding.iBaseLayout.groupNoConnection.visibility = View.GONE
    }

    private fun showResult() {
        changeComponentVisibility(View.GONE, View.VISIBLE)
    }

    private fun showNoConnection(message: String) {
        binding.iBaseLayout.tvNoConection.text = message
        changeComponentVisibility(View.VISIBLE, View.GONE)
    }

    private fun showGenericError(message: String) {
        val sortDialog = AlertDialog.Builder(context)
                .setTitle(GENERIC_MSG_ERROR_TITLE)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_ok, null)
                .create()

        sortDialog.show()
    }

    private fun changeComponentVisibility(gone: Int, visible: Int) {
        binding.iBaseLayout.groupNoConnection.visibility = gone
        binding.rvMovies.visibility = visible
    }

    private fun tryAgain() {
        binding.iBaseLayout.btTryAgain.setOnClickListener { mViewModel.tryAgain() }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mViewModel
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sort, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.m_sort) {
            val values = arrayOf<CharSequence>("Most Popular", "Highest Rated", "Favorites")
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
                mViewModel.setMovieSortBy(FILTER_MOST_POPULAR)
                mViewModel.selectedFilterIndex = 0
            }
            INDEX_FILTER_HIGHEST_RATED -> {
                mViewModel.setMovieSortBy(FILTER_HIGHEST_RATED)
                mViewModel.selectedFilterIndex = 1
            }
            INDEX_FILTER_FAVORITES -> {
                mViewModel.setMovieSortBy(FILTER_FAVORITES)
                mViewModel.selectedFilterIndex = 2
            }
        }
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(movie)
        findNavController().navigate(action)
    }
}
