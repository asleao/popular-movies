package br.com.popularmovies.movies.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.popularmovies.R
import br.com.popularmovies.core.network.NETWORK_ERROR_CODE
import br.com.popularmovies.data.model.OldResource
import br.com.popularmovies.movies.Constants.FILTER_FAVORITES
import br.com.popularmovies.movies.Constants.FILTER_HIGHEST_RATED
import br.com.popularmovies.movies.Constants.FILTER_MOST_POPULAR
import br.com.popularmovies.movies.Constants.GENERIC_MSG_ERROR_TITLE
import br.com.popularmovies.movies.Constants.INDEX_FILTER_FAVORITES
import br.com.popularmovies.movies.Constants.INDEX_FILTER_HIGHEST_RATED
import br.com.popularmovies.movies.Constants.INDEX_FILTER_MOST_POPULAR
import br.com.popularmovies.movies.Constants.TITLE_DIALOG_FILTER
import br.com.popularmovies.movies.adapters.MovieAdapter
import br.com.popularmovies.movies.adapters.MovieClickListener
import br.com.popularmovies.movies.viewmodel.MovieViewModel
import br.com.popularmovies.movies.viewmodel.factories.MovieFactory
import br.com.popularmovies.services.movieService.response.Movie
import br.com.popularmovies.services.movieService.response.Movies
import br.com.popularmovies.services.movieService.source.MovieRepository
import br.com.popularmovies.services.movieService.source.local.MovieLocalDataSource
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource

class MovieFragment : Fragment(), MovieClickListener {

    private lateinit var mViewModel: MovieViewModel
    private lateinit var mMoviesRecyclerView: RecyclerView
    private lateinit var moviesObserver: Observer<OldResource<Movies>>
    private lateinit var mNoConnectionGroup: Group
    private lateinit var mTryAgainButton: Button
    private lateinit var mNoConnectionText: TextView
    private lateinit var mProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setHasOptionsMenu(true)
        setupObservers()
    }

    private fun setupObservers() {
        moviesObserver = Observer { moviesResource ->
            if (moviesResource != null)
                when (moviesResource.status) {
                    OldResource.Status.LOADING -> showLoading()
                    OldResource.Status.SUCCESS -> {
                        hideLoading()
                        if (moviesResource.data != null) {
                            val mMovieAdapter = MovieAdapter(moviesResource.data.movies, this@MovieFragment)
                            mMoviesRecyclerView.adapter = mMovieAdapter
                            showResult()
                        }
                    }
                    OldResource.Status.ERROR -> {
                        hideLoading()
                        val error = moviesResource.error
                        if (error != null) {
                            if (error.statusCode == NETWORK_ERROR_CODE) {
                                showNoConnection(error.statusMessage)
                                tryAgain()
                            } else {
                                showGenericError(error.statusMessage)
                            }
                        }
                    }
                }
        }
    }


    private fun hideLoading() {
        mProgressBar.visibility = View.GONE
    }

    private fun showLoading() {
        mProgressBar.visibility = View.VISIBLE
        mMoviesRecyclerView.visibility = View.GONE
        mNoConnectionGroup.visibility = View.GONE
    }

    private fun showResult() {
        changeComponentVisibility(View.GONE, View.VISIBLE)
    }

    private fun showNoConnection(message: String) {
        mNoConnectionText.text = message
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
        mNoConnectionGroup.visibility = gone
        mMoviesRecyclerView.visibility = visible
    }


    private fun tryAgain() {
        mTryAgainButton.setOnClickListener { mViewModel.tryAgain() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        setupFields(view)
        setupMoviesList(view)
        val mMovieRepository = MovieRepository.getInstance(MovieLocalDataSource.getInstance(requireActivity().applicationContext), MovieRemoteDataSource.getInstance())
        mViewModel = ViewModelProviders.of(this,
                MovieFactory(mMovieRepository)).get(MovieViewModel::class.java)
        mViewModel.movies.observe(viewLifecycleOwner, moviesObserver)
        return view
    }

    private fun setupMoviesList(view: View) {
        mMoviesRecyclerView = view.findViewById(R.id.rv_movies)
        mMoviesRecyclerView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
    }

    private fun setupFields(view: View) {
        mNoConnectionGroup = view.findViewById(R.id.group_no_connection)
        mNoConnectionText = view.findViewById(R.id.tv_no_conection)
        mTryAgainButton = view.findViewById(R.id.bt_try_again)
        mProgressBar = view.findViewById(R.id.pb_base)
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
