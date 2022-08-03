package br.com.popularmovies.moviedetail.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.popularmovies.appComponent
import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel
import br.com.popularmovies.ui.theme.AppTheme
import br.com.popularmovies.utils.youtube

class MovieDetailFragment : Fragment() {

    private val args by navArgs<MovieDetailFragmentArgs>()

    private val viewModel: MovieDetailViewModel by lazy {
        appComponent.movieDetailViewModelFactory.create(args.movieId)
    }

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
        viewModel.playTrailer.observe(this) { trailerKey ->
            trailerKey?.let {
                requireContext().youtube(trailerKey)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    MovieScreen(viewModel = viewModel)
                }
            }
        }
    }
}
