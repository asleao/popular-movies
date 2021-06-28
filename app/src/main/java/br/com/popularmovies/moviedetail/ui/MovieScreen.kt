package br.com.popularmovies.moviedetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.popularmovies.moviedetail.reviews.ui.MovieReview
import br.com.popularmovies.moviedetail.trailers.ui.MovieTrailerCard
import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel

@Composable
fun MovieScreen(viewModel: MovieDetailViewModel) {
    val movie by viewModel.movie.observeAsState()
    val trailers by viewModel.trailers.observeAsState()
    val reviews by viewModel.reviews.observeAsState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        movie?.let {
            item {
                MovieDetail(it)
            }
        }
        trailers?.let { trailers ->
            //TODO checkout stickyHeaders
            if (trailers.isNotEmpty()) {
                item {
                    Text(
                        "Trailers",
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp),
                        style = MaterialTheme.typography.h3,
                        color = MaterialTheme.colors.onPrimary
                    )
                    LazyRow(
                        state = rememberLazyListState(),
                        contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(trailers) { movieTrailer ->
                            MovieTrailerCard(
                                movieTrailer,
                                onClick = {}
                            )
                        }
                    }
                }
            }
        }
        reviews?.let { reviews ->
            if (reviews.isNotEmpty()) {
                item {
                    Text(
                        "Reviews",
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp),
                        style = MaterialTheme.typography.h3,
                        color = MaterialTheme.colors.onPrimary
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
    }
}