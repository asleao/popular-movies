package br.com.popularmovies.utils

import android.content.res.Resources
import android.graphics.drawable.Drawable
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.facebook.shimmer.ShimmerFrameLayout

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
//
//fun shimmerRequestListener(shimmerFrameLayout: ShimmerFrameLayout) =
//    object : RequestListener<Drawable> {
//        override fun onLoadFailed(
//            e: GlideException?,
//            model: Any?,
//            target: Target<Drawable>?,
//            isFirstResource: Boolean
//        ): Boolean {
//            shimmerFrameLayout.setShimmer(null)
//            shimmerFrameLayout.stopShimmer()
//            return false
//        }
//
//        override fun onResourceReady(
//            resource: Drawable?,
//            model: Any?,
//            target: Target<Drawable>?,
//            dataSource: DataSource?,
//            isFirstResource: Boolean
//        ): Boolean {
//            shimmerFrameLayout.setShimmer(null)
//            shimmerFrameLayout.stopShimmer()
//            return false
//        }
//
//    }