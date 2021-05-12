package br.com.popularmovies.moviedetail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel

@Composable
fun MovieScreen(viewModel: MovieDetailViewModel) {
    val movie by viewModel.movie.observeAsState()
    movie?.let {
        MovieDetail(it)
    }
}