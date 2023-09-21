package br.com.popularmovies.searchmovies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import br.com.popularmovies.core.designsystem.AppTheme
import javax.inject.Inject

class SearchMoviesFragment @Inject constructor(
    //viewModelFactory: MovieDetailViewModel.Factory
) : Fragment() {

    //  private val args by navArgs<MovieDetailFragmentArgs>()

    //private val viewModel: MovieDetailViewModel by lazy {
    //    viewModelFactory.create(args.movieId)
    //}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {

                }
            }
        }
    }
}
