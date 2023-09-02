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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.popularmovies.core.ui.components.ErrorView
import br.com.popularmovies.moviedetails.ui.reviews.ui.MovieReview
import br.com.popularmovies.moviedetails.ui.trailers.ui.MovieTrailerCard
import br.com.popularmovies.moviedetails.viewmodel.MovieDetailViewModel
import br.com.popularmovies.moviedetails.viewmodel.MovieUiState
import br.com.popularmovies.moviedetails.viewmodel.ReviewUiState
import br.com.popularmovies.moviedetails.viewmodel.TrailerUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    onBack: () -> Unit
) {
    val movieState by viewModel.movieUiState.collectAsStateWithLifecycle(MovieUiState.Loading)
    val trailersState by viewModel.trailersUiState.collectAsStateWithLifecycle(TrailerUiState.Loading)
    val reviewsState by viewModel.reviewsUiState.collectAsStateWithLifecycle(ReviewUiState.Loading)

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
            if (movieState == MovieUiState.Error) {
                //TODO Add String resource
                ErrorView(
                    imageRes = br.com.popularmovies.core.ui.R.drawable.ic_cloud_off,
                    description = "Ocorreu um erro",
                    buttonText = "Tentar novamente",
                    buttonClickListener = {}
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
                        MovieUiState.Loading -> {

                        }

                        is MovieUiState.Success -> {
                            val movie = (movieState as MovieUiState.Success).movie
                            item {
                                MovieDetail(movie)
                            }
                        }

                        MovieUiState.Error -> {
                            // Do nothing
                        }
                    }

                    when (trailersState) {
                        TrailerUiState.Loading -> {

                        }

                        is TrailerUiState.Success -> {
                            val trailers = (trailersState as TrailerUiState.Success).trailers
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
                                                    viewModel.playTrailer(movieTrailer.key)
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    when (reviewsState) {
                        ReviewUiState.Loading -> {

                        }

                        is ReviewUiState.Success -> {
                            val reviews = (reviewsState as ReviewUiState.Success).reviews
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
                    }
                }
            }
        }
    )
}