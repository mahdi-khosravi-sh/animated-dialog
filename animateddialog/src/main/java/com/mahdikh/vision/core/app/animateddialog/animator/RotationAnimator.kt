package com.mahdikh.vision.core.app.animateddialog.animator

import android.view.View
import android.view.ViewPropertyAnimator

class RotationAnimator : Animator() {
    override fun animate(animator: ViewPropertyAnimator) {
        if (mode == MODE_IN) {
            animator
                .alpha(1.0F)
                .rotation(0.0F)
                .translationX(0.0F)
        } else {
            animator
                .alpha(0.0F)
                .rotation(360.0F)
                .translationX(400.0F)
                .scaleX(0.4F)
                .scaleY(0.4F)
        }
    }

    override fun onBeforeAnimate(view: View) {
        if (mode == MODE_IN) {
            view.alpha = 0.0f
            view.rotation = -360.0F
            view.translationX = -400.0F
        }
    }
}