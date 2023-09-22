package br.com.popularmovies.searchmovies.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.popularmovies.core.designsystem.AppTheme
import br.com.popularmovies.core.designsystem.previews.ThemePreview
import br.com.popularmovies.core.ui.components.movie.Movies
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.searchmovies.viewmodel.SearchMoviesViewModel
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMoviesScreen(viewModel: SearchMoviesViewModel) {

    val searchMoviesUiState = SearchMoviesUiState(
        viewModel.isSearchVisible,
        viewModel.query,
        viewModel::onQueryChanged,
        viewModel.movies
    )

    val isSearching by searchMoviesUiState.isSearchVisible.collectAsStateWithLifecycle(initialValue = false)
    val query by searchMoviesUiState.query.collectAsStateWithLifecycle(initialValue = "")
    val searchMoviesState by searchMoviesUiState.movies.collectAsStateWithLifecycle(initialValue = emptyList())

    Scaffold(
        modifier = Modifier,
        content = { innerPadding ->
            Column(modifier = Modifier) {
                SearchBar(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    query = query,
                    onQueryChange = viewModel::onQueryChanged,
                    onSearch = {},
                    active = false,
                    onActiveChange = {},
                    placeholder = { Text(text = "Search") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = {
                                viewModel.onQueryChanged("")
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Close"
                                )
                            }
                        }
                    }
                ) { }
                if (isSearching && query.isNotEmpty()) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                } else {
                    Movies(
                        modifier = Modifier.padding(innerPadding),
                        movies = searchMoviesState,
                        onMovieSelected = {}
                    )
                }
            }
        }
    )
}

data class SearchMoviesUiState(
    val isSearchVisible: Flow<Boolean>,
    val query: Flow<String>,
    val onQueryChange: (String) -> Unit,
    val movies: Flow<List<Movie>>
)

@ThemePreview
@Composable
fun searchMoviesPreview() {
    AppTheme {
        //SearchMoviesScreen()
    }
}