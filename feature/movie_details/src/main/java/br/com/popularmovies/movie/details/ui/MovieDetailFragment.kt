package br.com.popularmovies.movie.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.popularmovies.common.utils.youtube
import br.com.popularmovies.core.designsystem.AppTheme
import br.com.popularmovies.movie.details.viewmodel.MovieDetailViewModel
import javax.inject.Inject

class MovieDetailFragment @Inject constructor(
    viewModelFactory: MovieDetailViewModel.Factory
) : Fragment() {

    private val args by navArgs<MovieDetailFragmentArgs>()

    private val viewModel: MovieDetailViewModel by lazy {
        viewModelFactory.create(args.movieId)
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
