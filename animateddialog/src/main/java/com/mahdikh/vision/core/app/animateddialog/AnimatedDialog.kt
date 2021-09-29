package com.mahdikh.vision.core.app.animateddialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Message
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.FloatRange
import com.mahdikh.vision.core.app.animateddialog.animator.Animator

open class AnimatedDialog {
    lateinit var vDialog: VDialog
    private var onDismissing = false
    lateinit var contentView: View

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
    var callback: DialogCallback? = null

    @DimRange
    var dimAmount: Float = 0.0F
    var background = -1
    var location: IntArray? = null

    constructor(context: Context) {
        init(context)
    }

    constructor(context: Context, contentView: View) {
        this.contentView = contentView
        init(context)
    }

    constructor(context: Context, layoutResId: Int) {
        contentView = LayoutInflater.from(context).inflate(layoutResId, null, false)
        init(context)
    }

    constructor(context: Context, layoutResId: Int, dialogCallback: DialogCallback) {
        contentView = LayoutInflater.from(context).inflate(layoutResId, null, false)
        this.callback = dialogCallback
        init(context)
    }

    private fun init(context: Context) {
        vDialog = object : VDialog(context) {
            override fun handleDismiss() {
                this@AnimatedDialog.dismiss()
            }
        }
//        vDialog.setContentView(contentView)
    }

    open fun show() {
        show(true)
    }

    open fun show(animated: Boolean) {
        onBeforeShow()
        vDialog.show()
        val window = vDialog.window
        if (window != null) {
            val p = window.attributes
            p.dimAmount = dimAmount
            if (location != null) {
                p.gravity = Gravity.TOP or Gravity.START
                p.x = location!![0]
                p.y = location!![1]
            } else {
                p.gravity = Gravity.CENTER
            }
            p.verticalMargin = 0f
            p.horizontalMargin = 0f
            window.attributes = p
            if (background == NO_BACKGROUND) {
                window.decorView.setBackgroundColor(Color.TRANSPARENT)
            } else if (background != -1) {
                window.setBackgroundDrawableResource(background)
            }
            if (animated) {
                animateDialog()
            }
        }
        onShow()
    }

    private fun animateDialog() {
        val window = vDialog.window
        if (window != null) {
            enterAnimator?.animate(window.decorView)
        }
    }

    open fun dismiss() {
        if (onDismissing) return
        val window = vDialog.window
        if (window != null) {
            exitAnimator?.run {
                onDismissing = true
                animate(window.decorView) {
                    dismissNow()
                }
            } ?: kotlin.run {
                dismissNow()
            }
        }
    }

    /*open fun cancel() {
    }

    open fun cancel(cancelAction: Runnable) {
        vDialog.setOnCancelListener {
            cancelAction.run()
            vDialog.setOnCancelListener(null)
        }
        vDialog.cancel()
    }*/

    open fun dismissNow() {
        vDialog.dismissNow()
        onDismiss()
    }

    fun dismissNow(dismissAction: Runnable) {
        vDialog.setOnDismissListener {
            dismissAction.run()
            onDismissing = false
        }
        dismissNow()
    }

    fun findViewById(id: Int): View {
        return contentView.findViewById(id)
    }

    @CallSuper
    protected open fun onBeforeShow() {
        callback?.onBeforeShow(contentView)
    }

    @CallSuper
    protected open fun onShow() {
        callback?.onShow(contentView)
    }

    @CallSuper
    protected open fun onDismiss() {
        callback?.onDismiss()
    }

    fun setCancelable(cancelable: Boolean) {
        vDialog.setCancelable(cancelable)
    }

    fun setTitle(title: CharSequence?) {
        vDialog.setTitle(title)
    }

    fun setTitle(titleId: Int) {
        vDialog.setTitle(titleId)
    }

    fun setMessage(message: CharSequence?) {
        vDialog.setMessage(message)
    }

    open fun setCancelMessage(msg: Message?) {
        vDialog.setCancelMessage(msg)
    }

    open fun setDismissMessage(msg: Message?) {
        vDialog.setDismissMessage(msg)
    }


    open fun setButton(whichButton: Int, text: CharSequence?, msg: Message?) {
        vDialog.setButton(whichButton, text, msg)
    }

    open fun setButton(
        whichButton: Int,
        text: CharSequence?,
        listener: DialogInterface.OnClickListener?
    ) {
        vDialog.setButton(whichButton, text, listener)
    }

    open fun setButton(
        whichButton: Int, text: CharSequence?, icon: Drawable?,
        listener: DialogInterface.OnClickListener?
    ) {
        vDialog.setButton(whichButton, text, icon, listener)
    }

    open fun setIcon(resId: Int) {
        vDialog.setIcon(resId)
    }

    open fun setIcon(icon: Drawable?) {
        vDialog.setIcon(icon)
    }

    open fun setCustomTitle(customTitleView: View?) {
        vDialog.setCustomTitle(customTitleView)
    }

    interface DialogCallback {
        fun onBeforeShow(contentView: View)
        fun onShow(contentView: View)
        fun onDismiss()
    }

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @FloatRange(from = 0.0, to = 1.0)
    annotation class DimRange

    companion object {
        const val NO_BACKGROUND = -2
    }
}