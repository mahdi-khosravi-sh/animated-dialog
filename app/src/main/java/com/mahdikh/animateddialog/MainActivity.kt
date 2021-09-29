package com.mahdikh.animateddialog

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mahdikh.vision.core.app.animateddialog.AnimatedDialog
import com.mahdikh.vision.core.app.animateddialog.animator.FoldYAnimator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.showDialog).setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        AnimatedDialog(this)
            .setView(R.layout.dialog_layout)
            .setEnterAnimator(FoldYAnimator())
            .setExitAnimator(FoldYAnimator()).show()
    }
}