package com.mahdikh.vision.core.app.animateddialog.animator

import android.view.View
import android.view.ViewPropertyAnimator

class ScaleAnimator : Animator() {
    override fun animate(animator: ViewPropertyAnimator) {
        if (mode == MODE_IN) {
            animator
                .alpha(1F)
                .scaleX(1.0F)
                .scaleY(1.0F)
        } else {
            animator
                .alpha(0.0F)
                .scaleX(0.6F)
                .scaleY(0.6F)
        }
    }

    override fun onBeforeAnimate(view: View) {
        if (mode == MODE_IN) {
            view.alpha = 0.0F
            view.scaleX = 0.2F
            view.scaleY = 0.2F
        }
    }
}