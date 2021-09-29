package com.mahdikh.vision.core.app.animateddialog.animator

import android.animation.TimeInterpolator
import android.view.View
import android.view.ViewPropertyAnimator

abstract class Animator {
    private lateinit var animator: ViewPropertyAnimator
    var mode: Int = -1
    var startDelay: Long = 0
    var duration: Long = 300
    var interpolator: TimeInterpolator? = null

    private fun init(view: View) {
        animator = view.animate()
            .setDuration(duration)
            .setStartDelay(startDelay)
            .setInterpolator(interpolator)
    }

    fun animate(view: View, endAction: Runnable) {
        onBeforeAnimate(view)
        init(view)
        animate(animator)
        animator.withEndAction(endAction)
    }

    fun animate(view: View) {
        onBeforeAnimate(view)
        init(view)
        animate(animator)
    }

    protected abstract fun animate(animator: ViewPropertyAnimator)

    protected abstract fun onBeforeAnimate(view: View)

    companion object {
        const val MODE_IN = 0
        const val MODE_OUT = 1
    }
}