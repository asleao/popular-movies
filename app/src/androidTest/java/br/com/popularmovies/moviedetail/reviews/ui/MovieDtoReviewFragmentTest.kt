package br.com.popularmovies.moviedetail.reviews.ui

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import br.com.popularmovies.R
import io.mockk.mockk
import org.hamcrest.CoreMatchers.not
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MovieDtoReviewFragmentTest {
    private val navController = mockk<NavController>(relaxed = true)


    @BeforeEach
    fun init() {
        val args = MovieReviewFragmentArgs.fromBundle(Bundle().apply { putInt("movieId", 429203) })
        launchFragmentInContainer<MovieReviewFragment>(
            args.toBundle()
        ).onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun checkFields() {
        Espresso.onView(ViewMatchers.withId(R.id.rv_reviews))
            .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
    }
}