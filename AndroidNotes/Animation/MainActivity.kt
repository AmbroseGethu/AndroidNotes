package com.example.newanimatedcolorchange

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private val shapes = arrayOf(R.drawable.ic_square, R.drawable.ic_circle, R.drawable.ic_triangle)
    private var currentShapeIndex = 0
    private var lastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val shape = findViewById<ImageView>(R.id.shapeImageView)
        val startButton = findViewById<Button>(R.id.startButton)

        // Set the initial shape
        shape.setImageResource(shapes[currentShapeIndex])
        shape.setOnClickListener{
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                // Double-click detected, change the shape
                currentShapeIndex = (currentShapeIndex + 1) % shapes.size
                shape.setImageResource(shapes[currentShapeIndex])
            }
            lastClickTime = currentTime

        }
        startButton.setOnClickListener {
            // Single click, start animation
            animateAndChangeShape(shape, 0)
         }
    }

    private fun animateAndChangeShape(shape: ImageView, noOfRotations: Int) {
        if(noOfRotations != 3) {
            // Define the center of rotation
            val centerX = (100).toFloat()
            val centerY = (-300).toFloat() // 50dp above the shape

            // Use ViewPropertyAnimator for smoother rotation animation
            shape.animate()
                .rotationBy(360f) // 3 full rotations
                .scaleX(1.5f) // Scale up to 1.5 times the original size
                .scaleY(1.5f)
                .setDuration(1000) // Set the duration for the entire animation in milliseconds
                .withStartAction {
                    // Set the pivot point for rotation
                    shape.pivotX = centerX
                    shape.pivotY = centerY
                }
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        // Change the shape after the rotation completes
                        currentShapeIndex = (currentShapeIndex + 1) % shapes.size
                        shape.setImageResource(shapes[currentShapeIndex])
                        shape.animate().scaleX(1f).scaleY(1f)
                        animateAndChangeShape(shape, noOfRotations + 1)
                    }
                })
                .start()
        }

    }
    companion object {
        private const val DOUBLE_CLICK_TIME_DELTA: Long = 300 // Time in milliseconds for double-click detection
    }
}
