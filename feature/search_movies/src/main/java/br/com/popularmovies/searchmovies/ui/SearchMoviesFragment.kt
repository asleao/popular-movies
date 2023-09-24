package br.com.popularmovies.searchmovies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import br.com.popularmovies.core.designsystem.AppTheme
import br.com.popularmovies.feature.moviedetails.api.MovieDetailsFeatureApi
import br.com.popularmovies.searchmovies.viewmodel.SearchMoviesViewModel
import javax.inject.Inject
import javax.inject.Provider

class SearchMoviesFragment @Inject constructor(
    private val movieDetailsFeatureApi: MovieDetailsFeatureApi,
    viewModelFactory: Provider<SearchMoviesViewModel>
) : Fragment() {

    private val viewModel: SearchMoviesViewModel by lazy {
        viewModelFactory.get()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    SearchMoviesScreen(viewModel) { movie ->
                        val request = NavDeepLinkRequest.Builder
                            .fromUri(movieDetailsFeatureApi.deeplink(movie.id.toString()))
                            .build()
                        findNavController().navigate(request)
                    }
                }
            }
        }
    }
}
