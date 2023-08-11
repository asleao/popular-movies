package br.com.popularmovies.home.utils

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

//TODO move this to the correct module
class SpacingItemDecoration(private val spacingItemDecorationType: SpacingItemDecorationType) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemCount = parent.adapter?.itemCount
        val itemPosition = parent.getChildAdapterPosition(view)

        if (itemPosition == 0) {
            spacingItemDecorationType.setSpacingFirstPosition(outRect)
        }

        if (itemPosition > 0 && itemPosition != itemCount) {
            spacingItemDecorationType.setSpacingOtherPosition(outRect)
        }
    }
}

sealed class SpacingItemDecorationType {
    abstract val spacingSize: Int
    abstract fun setSpacingFirstPosition(outRect: Rect)
    abstract fun setSpacingOtherPosition(outRect: Rect)

    data class Horizontal(
        @DimenRes override val spacingSize: Int
    ) : SpacingItemDecorationType() {
        override fun setSpacingFirstPosition(outRect: Rect) {
            outRect.right = spacingSize
        }

        override fun setSpacingOtherPosition(outRect: Rect) {
            with(outRect) {
                left = spacingSize
                right = spacingSize
            }
        }
    }

    data class Vertical(
        @DimenRes override val spacingSize: Int
    ) : SpacingItemDecorationType() {
        override fun setSpacingFirstPosition(outRect: Rect) {
            outRect.bottom = spacingSize
        }

        override fun setSpacingOtherPosition(outRect: Rect) {
            with(outRect) {
                top = spacingSize
                bottom = spacingSize
            }
        }
    }
}