package com.mahdikh.vision.core.app.animateddialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Message
import android.view.Gravity
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.core.content.res.ResourcesCompat
import com.mahdikh.vision.core.app.animateddialog.animator.Animator

open class AnimatedDialog : VDialog {
    private var onDismissing = false
    var enterAnimator: Animator? = null
    var exitAnimator: Animator? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    final override fun show() {
        super.show()
        val window = window
        if (window != null) {
            enterAnimator?.animate(window.decorView)
        }
        onShow()
    }

    override fun cancel() {
        super.cancel()
        onCancel()
    }

    override fun dismiss() {
        super.dismiss()
        onDismiss()
    }

    open fun onShow() {}

    open fun onCancel() {}

    open fun onDismiss() {}

    override fun handleDismiss() {
        handleClose(false)
    }

    override fun handleCancel() {
        handleClose(true)
    }

    private fun handleClose(cancel: Boolean) {
        if (onDismissing) return
        val window = window
        if (window != null) {
            exitAnimator?.run {
                onDismissing = true
                animate(window.decorView) {
                    if (cancel) {
                        forceCancel()
                    } else {
                        forceDismiss()
                    }
                }
            } ?: kotlin.run {
                if (cancel) {
                    forceCancel()
                } else {
                    forceDismiss()
                }
            }
        }
    }

    open fun setEnterAnimator(enterAnimator: Animator?): AnimatedDialog {
        enterAnimator?.mode = Animator.MODE_IN
        this.enterAnimator = enterAnimator
        return this
    }

    open fun setExitAnimator(exitAnimator: Animator?): AnimatedDialog {
        exitAnimator?.mode = Animator.MODE_OUT
        this.exitAnimator = exitAnimator
        return this
    }

    open fun setPositiveButton(
        text: CharSequence?,
        listener: DialogInterface.OnClickListener
    ): AnimatedDialog {
        setButton(DialogInterface.BUTTON_POSITIVE, text, listener)
        return this
    }

    open fun setNegativeButton(
        text: CharSequence?,
        listener: DialogInterface.OnClickListener
    ): AnimatedDialog {
        setButton(DialogInterface.BUTTON_NEGATIVE, text, listener)
        return this
    }

    open fun setNeutralButton(
        text: CharSequence?,
        listener: DialogInterface.OnClickListener
    ): AnimatedDialog {
        setButton(DialogInterface.BUTTON_NEUTRAL, text, listener)
        return this
    }

    open fun setPositiveButton(text: CharSequence?, msg: Message): AnimatedDialog {
        setButton(DialogInterface.BUTTON_POSITIVE, text, msg)
        return this
    }

    open fun setNegativeButton(text: CharSequence?, msg: Message): AnimatedDialog {
        setButton(DialogInterface.BUTTON_NEGATIVE, text, msg)
        return this
    }

    open fun setNeutralButton(text: CharSequence?, msg: Message): AnimatedDialog {
        setButton(DialogInterface.BUTTON_NEUTRAL, text, msg)
        return this
    }

    open fun setPositiveButton(
        text: CharSequence?,
        icon: Drawable?,
        listener: DialogInterface.OnClickListener
    ): AnimatedDialog {
        setButton(DialogInterface.BUTTON_POSITIVE, text, icon, listener)
        return this
    }

    open fun setNegativeButton(
        text: CharSequence?,
        icon: Drawable?,
        listener: DialogInterface.OnClickListener
    ): AnimatedDialog {
        setButton(DialogInterface.BUTTON_NEGATIVE, text, icon, listener)
        return this
    }

    open fun setNeutralButton(
        text: CharSequence?,
        icon: Drawable?,
        listener: DialogInterface.OnClickListener
    ): AnimatedDialog {
        setButton(DialogInterface.BUTTON_NEUTRAL, text, icon, listener)
        return this
    }

    open fun forceDismiss(dismissAction: Runnable) {
        super.setOnDismissListener {
            dismissAction.run()
            onDismissing = false
        }
        forceDismiss()
    }

    open fun setView(layoutResID: Int): AnimatedDialog {
        super.setView(
            LayoutInflater.from(context).inflate(
                layoutResID, null, false
            )
        )
        return this
    }

    open fun setAlpha(alpha: Float): AnimatedDialog {
        val w = window
        w?.let {
            val attrs = it.attributes
            attrs.alpha = alpha
        }
        return this
    }

    open fun setLocation(x: Int, y: Int): AnimatedDialog {
        val w = window
        w?.let {
            val attrs = it.attributes
            attrs.gravity = Gravity.TOP or Gravity.START
            attrs.x = x
            attrs.y = y
        }
        return this
    }

    open fun setBackgroundColor(@ColorInt color: Int): AnimatedDialog {
        setBackgroundDrawable(ColorDrawable(color))
        return this
    }

    open fun setBackgroundRes(@DrawableRes drawableResId: Int): AnimatedDialog {
        val background = ResourcesCompat.getDrawable(
            context.resources, drawableResId,
            context.theme
        )
        setBackgroundDrawable(background)
        return this
    }

    open fun setDimAmount(@DimRange dimAmount: Float): AnimatedDialog {
        window?.setDimAmount(dimAmount)
        return this
    }

    open fun setBackgroundDrawable(drawable: Drawable?): AnimatedDialog {
        window?.setBackgroundDrawable(drawable)
        return this
    }

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @FloatRange(from = 0.0, to = 1.0)
    annotation class DimRange
}