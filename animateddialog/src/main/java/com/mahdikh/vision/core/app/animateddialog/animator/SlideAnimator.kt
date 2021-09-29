package com.mahdikh.vision.core.app.animateddialog.animator

import android.view.View
import android.view.ViewPropertyAnimator

class SlideAnimator : Animator() {
    override fun animate(animator: ViewPropertyAnimator) {
        if (mode == MODE_IN) {
            animator.alpha(1.0F).translationX(0.0F)
        } else {
            animator.alpha(0.0F).translationX(400.0F)
        }
    }

    override fun onBeforeAnimate(view: View) {
        if (mode == MODE_IN) {
            view.alpha = 0f
            view.translationX = -400f
        } else {
            view.alpha = 1f
        }
    }
}