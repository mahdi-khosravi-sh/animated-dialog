package com.mahdikh.vision.core.app.animateddialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Message
import android.view.Gravity
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.core.content.res.ResourcesCompat
import com.mahdikh.vision.core.app.animateddialog.animator.Animator

open class AnimatedDialog : VDialog {
    private var onDismissing = false
    var callback: DialogCallback? = null
    var enterAnimator: Animator? = null
        set(value) {
            field = value
            field?.mode = Animator.MODE_IN
        }
    var exitAnimator: Animator? = null
        set(value) {
            field = value
            field?.mode = Animator.MODE_OUT
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    final override fun show() {
        onBeforeShow()
        super.show()
        val window = window
        if (window != null) {
            enterAnimator?.animate(window.decorView)
        }
        onShow()
    }

    override fun handleDismiss() {
        if (onDismissing) return
        val window = window
        if (window != null) {
            exitAnimator?.run {
                onDismissing = true
                animate(window.decorView) {
                    forceDismiss()
                }
            } ?: kotlin.run {
                forceDismiss()
            }
        }
    }

    fun setPositiveButton(text: CharSequence?, listener: DialogInterface.OnClickListener) {
        setButton(DialogInterface.BUTTON_POSITIVE, text, listener)
    }

    fun setNegativeButton(text: CharSequence?, listener: DialogInterface.OnClickListener) {
        setButton(DialogInterface.BUTTON_NEGATIVE, text, listener)
    }

    fun setNeutralButton(text: CharSequence?, listener: DialogInterface.OnClickListener) {
        setButton(DialogInterface.BUTTON_NEUTRAL, text, listener)
    }

    fun setPositiveButton(text: CharSequence?, msg: Message) {
        setButton(DialogInterface.BUTTON_POSITIVE, text, msg)
    }

    fun setNegativeButton(text: CharSequence?, msg: Message) {
        setButton(DialogInterface.BUTTON_NEGATIVE, text, msg)
    }

    fun setNeutralButton(text: CharSequence?, msg: Message) {
        setButton(DialogInterface.BUTTON_NEUTRAL, text, msg)
    }

    fun setPositiveButton(
        text: CharSequence?,
        icon: Drawable?,
        listener: DialogInterface.OnClickListener
    ) {
        setButton(DialogInterface.BUTTON_POSITIVE, text, icon, listener)
    }

    fun setNegativeButton(
        text: CharSequence?,
        icon: Drawable?,
        listener: DialogInterface.OnClickListener
    ) {
        setButton(DialogInterface.BUTTON_NEGATIVE, text, icon, listener)
    }

    fun setNeutralButton(
        text: CharSequence?,
        icon: Drawable?,
        listener: DialogInterface.OnClickListener
    ) {
        setButton(DialogInterface.BUTTON_NEUTRAL, text, icon, listener)
    }

    override fun forceDismiss() {
        super.forceDismiss()
        onDismiss()
    }

    fun forceDismiss(dismissAction: Runnable) {
        super.setOnDismissListener {
            dismissAction.run()
            onDismissing = false
        }
        forceDismiss()
    }

    @CallSuper
    protected open fun onBeforeShow() {
        callback?.onBeforeShow()
    }

    @CallSuper
    protected open fun onShow() {
        callback?.onShow()
    }

    @CallSuper
    protected open fun onDismiss() {
        callback?.onDismiss()
    }

    open fun setView(layoutResID: Int) {
        super.setView(
            LayoutInflater.from(context).inflate(
                layoutResID, null, false
            )
        )
    }

    open fun setAlpha(alpha: Float) {
        val w = window
        w?.let {
            val attrs = it.attributes
            attrs.alpha = alpha
        }
    }

    open fun setLocation(x: Int, y: Int) {
        val w = window
        w?.let {
            val attrs = it.attributes
            attrs.gravity = Gravity.TOP or Gravity.START
            attrs.x = x
            attrs.y = y
        }
    }

    open fun setBackgroundColor(@ColorInt color: Int) {
        setBackgroundDrawable(ColorDrawable(color))
    }

    open fun setBackgroundRes(@DrawableRes drawableResId: Int) {
        val background = ResourcesCompat.getDrawable(
            context.resources, drawableResId,
            context.theme
        )
        setBackgroundDrawable(background)
    }

    open fun setDimAmount(@DimRange dimAmount: Float) {
        window?.setDimAmount(dimAmount)
    }

    open fun setBackgroundDrawable(drawable: Drawable?) {
        window?.setBackgroundDrawable(drawable)
    }

    @Deprecated("")
    final override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {

    }

    @Deprecated("")
    final override fun setOnShowListener(listener: DialogInterface.OnShowListener?) {

    }

    interface DialogCallback {
        fun onBeforeShow() {}

        fun onShow() {}

        fun onDismiss() {}
    }

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @FloatRange(from = 0.0, to = 1.0)
    annotation class DimRange
}