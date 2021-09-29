package com.mahdikh.vision.core.app.animateddialog.animator

import android.view.View
import android.view.ViewPropertyAnimator

class FoldAnimator : Animator() {
    override fun animate(animator: ViewPropertyAnimator) {
        if (mode == MODE_IN) {
            animator
                .alpha(1.0F)
                .scaleY(1.0F)
        } else {
            animator
                .alpha(0.2F)
                .scaleY(0.0F)
                .scaleX(1.3F)
        }
    }

    override fun onBeforeAnimate(view: View) {
        if (mode == MODE_IN) {
            view.alpha = 0.5F
            view.scaleY = 0.0F
        } else {
            view.alpha = 1.0F
            view.scaleY = 1.0F
        }
    }
}