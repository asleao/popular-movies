package br.com.popularmovies.moviedetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.popularmovies.core.ui.components.ErrorView
import br.com.popularmovies.moviedetails.ui.reviews.ui.MovieReview
import br.com.popularmovies.moviedetails.ui.trailers.ui.MovieTrailerCard
import br.com.popularmovies.moviedetails.viewmodel.MovieDetailUiState
import br.com.popularmovies.moviedetails.viewmodel.MovieDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    onBack: () -> Unit,
    onTryAgainClick: () -> Unit,
    onTrailerClick: (String) -> Unit
) {
    val movieState = viewModel.uiState

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        text = "Details",
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            if (movieState == MovieDetailUiState.Error) {
                //TODO Add String resource
                ErrorView(
                    imageRes = br.com.popularmovies.core.ui.R.drawable.ic_cloud_off,
                    description = "Ocorreu um erro",
                    buttonText = "Tentar novamente",
                    buttonClickListener = {
                        onTryAgainClick()
                    }
                )
            } else {
                LazyColumn(
                    state = rememberLazyListState(),
                    contentPadding = innerPadding,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    when (movieState) {
                        MovieDetailUiState.Loading -> {

                        }

                        is MovieDetailUiState.Success -> {
                            val movie = movieState.movie
                            item {
                                MovieDetail(movie)
                            }

                            val trailers = movieState.trailers
                            if (trailers.isNotEmpty()) {
                                item {
                                    Text(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp),
                                        text = "Trailers",
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    LazyRow(
                                        state = rememberLazyListState(),
                                        contentPadding = PaddingValues(horizontal = 16.dp),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        items(trailers) { movieTrailer ->
                                            MovieTrailerCard(
                                                movieTrailer,
                                                onClick = {
                                                    onTrailerClick(movieTrailer.key)
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            val reviews = movieState.reviews
                            if (reviews.isNotEmpty()) {
                                item {
                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp),
                                        text = "Reviews",
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }

                                items(reviews) { review ->
                                    MovieReview(
                                        movieReview = review,
                                        onClick = {}
                                    )
                                }
                            }
                        }

                        MovieDetailUiState.Error -> {
                            // Do nothing
                        }
                    }
                }
            }
        }
    )
}