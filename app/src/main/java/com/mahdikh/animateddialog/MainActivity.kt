package com.mahdikh.animateddialog

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mahdikh.vision.core.app.animateddialog.AnimatedDialog
import com.mahdikh.vision.core.app.animateddialog.animator.ScaleAnimator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.showDialog).setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialog = AnimatedDialog(this)
        dialog.exitAnimator = ScaleAnimator()
        dialog.enterAnimator = ScaleAnimator()
        dialog.setTitle("Hello World")
        dialog.setMessage("My name is mehdi khosravi")
        dialog.setNegativeButton("Cancel") { p0, _ -> p0.dismiss() }
        dialog.setPositiveButton("OK") { p0, _ -> p0.dismiss() }
        dialog.setNeutralButton("Later") { p0, _ -> p0.dismiss() }
        dialog.show()
    }
}