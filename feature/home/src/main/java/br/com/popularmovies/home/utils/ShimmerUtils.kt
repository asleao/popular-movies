package br.com.popularmovies.home.utils

import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
//TODO move this to the correct module
private val shimmer =
    Shimmer.AlphaHighlightBuilder()
        .setDuration(1800)
        .setBaseAlpha(0.7f)
        .setHighlightAlpha(0.6f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()

fun shimmerDrawable() = ShimmerDrawable().apply {
    setShimmer(shimmer)
}