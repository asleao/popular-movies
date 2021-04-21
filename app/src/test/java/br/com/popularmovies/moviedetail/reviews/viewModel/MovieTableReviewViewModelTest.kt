/*
package br.com.popularmovies.moviedetail.reviews.viewModel

import br.com.popularmovies.InstantExecutorExtension
import br.com.popularmovies.datasourceremote.config.Error
import br.com.popularmovies.datasourceremote.config.Resource
import br.com.popularmovies.repositories.movie.MovieRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class MovieTableReviewViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: MovieReviewViewModel
    private val movieRepository = mockk<br.com.popularmovies.repositories.movie.MovieRepositoryImpl>()

    @BeforeAll
    @ExperimentalCoroutinesApi
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    private fun setupViewModel() {
        viewModel = MovieReviewViewModel(movieRepository, 429203)
    }

    @AfterAll
    @ExperimentalCoroutinesApi
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Nested
    inner class GetReviews {
        @Test
        fun `when request is sucessfull, then reviews should be filled`() {
            every { runBlocking { movieRepository.getMovieReviews(429203) } } answers {
                br.com.popularmovies.datasourceremote.config.Resource.success(MovieReviews(emptyList()))
            }
            setupViewModel()
            viewModel.getReviews()

            assertThat(viewModel.loading.value).isTrue()
            assertThat(viewModel.reviews.value).isNotNull()
            assertThat(viewModel.error.value).isNull()
        }

        @Test
        fun `when request is not sucessfull, then error should be filled`() {
            every { runBlocking { movieRepository.getMovieReviews(429203) } } answers {
                br.com.popularmovies.datasourceremote.config.Resource.error(br.com.popularmovies.datasourceremote.config.Error(5, "Error", "Something went wrong"))
            }
            setupViewModel()
            viewModel.getReviews()

            assertThat(viewModel.loading.value).isTrue()
            assertThat(viewModel.reviews.value).isNull()
            assertThat(viewModel.error.value).isNotNull()
        }
    }

    @Nested
    inner class TryAgain {
        @Test
        fun `when tryAgain is called, then getReviews should be called as well`() {
            every { runBlocking { movieRepository.getMovieReviews(429203) } } answers {
                br.com.popularmovies.datasourceremote.config.Resource.success(MovieReviews(emptyList()))
            }
            setupViewModel()
            viewModel.tryAgain()

            assertThat(viewModel.loading.value).isTrue()
            assertThat(viewModel.reviews.value).isNotNull()
            assertThat(viewModel.error.value).isNull()
        }
    }
}*/
