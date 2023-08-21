package br.com.popularmovies.moviedetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.popularmovies.moviedetails.ui.reviews.ui.MovieReview
import br.com.popularmovies.moviedetails.ui.trailers.ui.MovieTrailerCard
import br.com.popularmovies.moviedetails.viewmodel.MovieDetailViewModel
import br.com.popularmovies.moviedetails.viewmodel.MovieUiState
import br.com.popularmovies.moviedetails.viewmodel.ReviewUiState
import br.com.popularmovies.moviedetails.viewmodel.TrailerUiState

@Composable
fun MovieScreen(viewModel: MovieDetailViewModel) {
    val movieState by viewModel.movieUiState.collectAsStateWithLifecycle(MovieUiState.Loading)
    val trailersState by viewModel.trailersUiState.collectAsStateWithLifecycle(TrailerUiState.Loading)
    val reviewsState by viewModel.reviewsUiState.collectAsStateWithLifecycle(ReviewUiState.Loading)
    Surface(
//        elevation = 2.dp,
        color = MaterialTheme.colorScheme.surface, // color will be adjusted for elevation
    ) {
        LazyColumn(
            state = rememberLazyListState(),
            contentPadding = PaddingValues(bottom = 16.dp),
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

                }
            }

            when (trailersState) {
                TrailerUiState.Loading -> {

                }

                is TrailerUiState.Success -> {
                    val trailers = (trailersState as TrailerUiState.Success).trailers
                    //TODO checkout stickyHeaders
                    if (trailers.isNotEmpty()) {
                        item {
                            Text(
                                "Trailers",
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 16.dp),
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            LazyRow(
                                state = rememberLazyListState(),
                                contentPadding = PaddingValues(
                                    top = 16.dp,
                                    start = 16.dp,
                                    end = 16.dp
                                ),
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

                TrailerUiState.Error -> {

                }
            }

            when (reviewsState) {
                ReviewUiState.Loading -> {

                }

                is ReviewUiState.Success -> {
                    val reviews = (reviewsState as ReviewUiState.Success).reviews
                    if (reviews.isNotEmpty()) {
                        item {
                            Text(
                                "Reviews",
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 16.dp),
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        items(reviews) { review ->
                            MovieReview(
                                movieReview = review,
                                onClick = {}
                            )
                        }
                    }
                }

                ReviewUiState.Error -> {

                }
            }
        }
    }
}