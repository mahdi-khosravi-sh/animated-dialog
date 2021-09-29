package com.mahdikh.vision.core.app.animateddialog

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

open class VDialog : AlertDialog {
    var dismissible = false

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    fun dismissNow() {
        dismissible = true
        dismiss()
    }

    protected open fun handleDismiss() {

    }

    override fun dismiss() {
        if (dismissible) {
            super.dismiss()
            return
        }
        handleDismiss()
    }
}