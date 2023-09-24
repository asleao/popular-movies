package br.com.popularmovies.core.ui.components.movie

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import br.com.popularmovies.core.designsystem.AppTheme
import br.com.popularmovies.core.designsystem.previews.ThemePreview
import br.com.popularmovies.core.ui.components.EmptyView
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieType
import org.joda.time.LocalDate
import java.math.BigDecimal

@Composable
fun Movies(
    modifier: Modifier,
    movies: LazyPagingItems<Movie>,
    onMovieSelected: (Movie) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = movies.loadState) {
        if (movies.loadState.refresh is LoadState.Error) {
            val error = (movies.loadState.refresh as LoadState.Error).error
            Toast.makeText(
                context, "Error:${error.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            movies.loadState.refresh is LoadState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            movies.itemCount == 0 -> {
                EmptyView(
                    modifier = Modifier,
                    message = "No movies",
                    imageVector = Icons.Filled.Movie
                )
            }

            else -> {
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(
                        count = movies.itemCount,
                        key = movies.itemKey { movie ->
                            movie.id
                        }) { index ->
                        val movie = movies[index]
                        movie?.let {
                            MovieCard(Modifier, movie, onMovieSelected)
                        }
                    }
                }
            }
        }
    }
}

@ThemePreview
@Composable
fun MoviesPreview() {
    AppTheme {
        val movie = Movie(
            votes = 1,
            id = 1L,
            type = MovieType.MostPopular,
            voteAverage = BigDecimal.TEN,
            originalTitle = "Movie Title",
            popularity = BigDecimal.TEN,
            poster = "",
            overview = "",
            releaseDate = LocalDate.now()
        )
        /*Movies(
            modifier = Modifier,
            movies = listOf(movie, movie)
        ) {}*/
    }
}