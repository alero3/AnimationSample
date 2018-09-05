package com.example.arota.animationsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.animation.ValueAnimator
import android.graphics.drawable.ClipDrawable
import android.os.Handler
import android.animation.Animator
import android.util.Log


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivty"
    private lateinit var circleFill: CircleFillView
    private lateinit var mImageDrawable: ClipDrawable
    private var mLevel: Int = 0
    private val mHandler = Handler()
    private lateinit var animateImage: Runnable
    private var oldAnimatedValue: Int = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        circleFill = findViewById<View>(R.id.circleFillView) as CircleFillView
        /*
        val imageView: ImageView = findViewById(R.id.imageView1)
        mImageDrawable = imageView.background as ClipDrawable
        mImageDrawable.level = 0

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(object : OnGlobalLayoutListener {

            override fun onGlobalLayout() {
                val height = imageView.height
                val width = imageView.width
                val xTop = imageView.left
                val xBottom = imageView.right
                val yTop = imageView.top
                val yBottom = imageView.bottom

                val imageViewYBottom = imageView.width / 2


                // don't forget to remove the listener to prevent being called again
                // by future layout events:
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this)
            }
        })
        */



        // FIRST animation circle filling
        val fillCircleAnimator = ValueAnimator.ofInt(0, 100)
        fillCircleAnimator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int

            if (circleFill.getWaterLevel(animatedValue) <= circleFill.getPumpBitmapBottom()) {
                oldAnimatedValue = animatedValue
                fillCircleAnimator.removeAllUpdateListeners()
                fillCircleAnimator.end()
            }

            // Update water level
            circleFill.setValue(animatedValue)
            circleFill.requestLayout()
        }

        // SECOND animation circle filling and image changing color
        val secondAnimator = ValueAnimator.ofInt(oldAnimatedValue, 100)
        secondAnimator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int

            if (circleFill.getWaterLevel(animatedValue) <= circleFill.getPumpBitmapTop()) {
                oldAnimatedValue = animatedValue
                secondAnimator.removeAllUpdateListeners()
                secondAnimator.end()
            }

            //TODO Update image color

            // Update water level
            circleFill.setValue(animatedValue)
            circleFill.requestLayout()
        }

        // THIRD animation circle filling
        val thirdAnimator = ValueAnimator.ofInt(oldAnimatedValue, 100)
        thirdAnimator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int


            //TODO Update image color

            // Update water level
            circleFill.setValue(animatedValue)
            circleFill.requestLayout()
        }



        fillCircleAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator) {

                Log.d (TAG, "First animation finished")
                secondAnimator.start()

            }

        })

        secondAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator) {
                Log.d (TAG, "Second animation finished")
                thirdAnimator.start()

            }

        })

        thirdAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator) {

                Log.d (TAG, "Third animation finished")
                //secondAnimator.start()



            }

        })

        fillCircleAnimator.duration = 2000
        secondAnimator.duration = 2000
        thirdAnimator.duration = 2000
        //fillCircleAnimator.repeatCount = ValueAnimator.INFINITE
        fillCircleAnimator.start()
    }


}
