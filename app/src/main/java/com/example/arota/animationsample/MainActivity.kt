package com.example.arota.animationsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.animation.ValueAnimator
import android.graphics.Rect
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.widget.ImageView


class MainActivity : AppCompatActivity() {

    private lateinit var circleFill: CircleFillView
    private lateinit var mImageDrawable: ClipDrawable
    private var mLevel: Int = 0
    private val mHandler = Handler()
    private lateinit var animateImage: Runnable

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        circleFill = findViewById<View>(R.id.circleFillView) as CircleFillView
        val imageview: ImageView = findViewById(R.id.imageView1)
        mImageDrawable = imageview.background as ClipDrawable
        mImageDrawable.level = 0


        // Animation circle filling and image
        val fillCircleAnimator = ValueAnimator.ofInt(0, 100)
        fillCircleAnimator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int

            val firstPosition = IntArray(2)
            val secondPosition = IntArray(2)


            /*
            mImageDrawable.getLocationOnScreen(firstPosition)
            circleFill.getLocationOnScreen(secondPosition)

            // Rect constructor parameters: left, top, right, bottom
            val rectFirstView = Rect(firstPosition[0], firstPosition[1],
                    firstPosition[0] + firstView.getMeasuredWidth(), firstPosition[1] + firstView.getMeasuredHeight())
            val rectSecondView = Rect(secondPosition[0], secondPosition[1],
                    secondPosition[0] + secondView.getMeasuredWidth(), secondPosition[1] + secondView.getMeasuredHeight())
            return rectFirstView.intersect(rectSecondView)
            */

            mImageDrawable.level = animatedValue*100
            mImageDrawable.invalidateDrawable(mImageDrawable)

            circleFill.setValue(animatedValue)
            circleFill.requestLayout()
        }

        fillCircleAnimator.duration = 2000
        fillCircleAnimator.repeatCount = ValueAnimator.INFINITE
        fillCircleAnimator.start()
    }


}
