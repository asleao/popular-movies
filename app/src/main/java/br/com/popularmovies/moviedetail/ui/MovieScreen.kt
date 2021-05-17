package br.com.popularmovies.moviedetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import br.com.popularmovies.moviedetail.trailers.ui.MovieTrailers
import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel

@Composable
fun MovieScreen(viewModel: MovieDetailViewModel) {
    val movie by viewModel.movie.observeAsState()
    val trailers by viewModel.trailers.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        movie?.let {
            MovieDetail(it)
        }
        trailers?.let {
            MovieTrailers(movieTrailers = it)
        }
    }
}