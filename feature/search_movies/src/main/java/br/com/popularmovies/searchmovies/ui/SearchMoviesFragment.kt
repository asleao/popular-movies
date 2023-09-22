package br.com.popularmovies.searchmovies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import br.com.popularmovies.core.designsystem.AppTheme
import br.com.popularmovies.searchmovies.viewmodel.SearchMoviesViewModel
import javax.inject.Inject
import javax.inject.Provider

class SearchMoviesFragment @Inject constructor(
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
                    SearchMoviesScreen(viewModel)
                }
            }
        }
    }
}
