package br.com.popularmovies.moviedetail.reviews.viewModel

import br.com.popularmovies.InstantExecutorExtension
import br.com.popularmovies.core.network.retrofit.model.Resource
import br.com.popularmovies.core.network.retrofit.model.Error
import br.com.popularmovies.services.movieService.response.MovieReviews
import br.com.popularmovies.services.movieService.source.MovieRepository
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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class MovieReviewViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: MovieReviewViewModel
    private val movieRepository = mockk<MovieRepository>()

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
                Resource.success(MovieReviews(emptyList()))
            }
            setupViewModel()
            viewModel.getReviews()

            assertThat(viewModel.reviews.value).isNotNull()
            assertThat(viewModel.error.value).isNull()
        }

        @Test
        fun `when request is not sucessfull, then error should be filled`() {
            every { runBlocking { movieRepository.getMovieReviews(429203) } } answers {
                Resource.error(Error(5, "Error", "Something went wrong"))
            }
            setupViewModel()
            viewModel.getReviews()

            assertThat(viewModel.reviews.value).isNull()
            assertThat(viewModel.error.value).isNotNull()
        }
    }
}