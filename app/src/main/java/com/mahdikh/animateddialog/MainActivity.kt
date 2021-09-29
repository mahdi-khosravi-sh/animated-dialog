package com.mahdikh.animateddialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.mahdikh.vision.core.app.animateddialog.AnimatedDialog
import com.mahdikh.vision.core.app.animateddialog.animator.FoldAnimator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.showDialog).setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialog = AnimatedDialog(this, R.layout.dialog_layout)

        dialog.enterAnimator = FoldAnimator().apply {
            interpolator = AnticipateOvershootInterpolator()
            duration = 400
        }

        dialog.exitAnimator = FoldAnimator().apply {
            interpolator = AnticipateOvershootInterpolator()
            duration = 400
        }
        dialog.setMessage(
            "My name is mehdi khosravi. " +
                    "whats your name My name is mehdi khosravi. " +
                    "whats your name My name is mehdi khosravi. whats your name "
        )
        dialog.setTitle("Hello World")
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel") { _, _ -> dialog.dismiss() }
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { _, _ -> dialog.dismiss() }
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Later") { _, _ -> dialog.dismiss() }
        dialog.dimAmount = 0.5F
        dialog.setIcon(R.drawable.ic_launcher_background)

        dialog.show()
    }
}