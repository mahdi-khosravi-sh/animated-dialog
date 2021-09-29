package com.mahdikh.vision.core.app.animateddialog.animator

import android.view.View
import android.view.ViewPropertyAnimator

class FadeAnimator : Animator() {
    override fun animate(animator: ViewPropertyAnimator) {
        animator.alpha(if (mode == MODE_IN) 1.0F else 0.0F)
    }

    override fun onBeforeAnimate(view: View) {
        view.alpha = if (mode == MODE_IN) 0.0F else 1.0F
    }
}