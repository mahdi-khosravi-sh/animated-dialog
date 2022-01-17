package com.mahdikh.vision.core.app.animateddialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import androidx.annotation.CallSuper

open class VDialog : Dialog {
    var dismissible = false

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    @CallSuper
    open fun forceDismiss() {
        dismissible = true
        dismiss()
    }

    @CallSuper
    open fun forceCancel() {
        dismissible = true
        cancel()
    }

    protected open fun handleDismiss() {
    }

    protected open fun handleCancel() {
    }

    override fun cancel() {
        if (dismissible) {
            super.cancel()
            return
        }
        handleCancel()
    }

    override fun dismiss() {
        if (dismissible) {
            super.dismiss()
            return
        }
        handleDismiss()
    }
}