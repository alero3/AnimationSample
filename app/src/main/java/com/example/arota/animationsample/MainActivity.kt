package com.example.arota.animationsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.animation.ValueAnimator
import android.graphics.drawable.ClipDrawable
import android.os.Handler
import android.animation.Animator
import android.util.Log
import android.widget.ImageView
import android.view.Gravity




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



        val img = findViewById<ImageView>(R.id.pump_iv)
        mImageDrawable = ClipDrawable(img.drawable, Gravity.BOTTOM, ClipDrawable.VERTICAL)
        mImageDrawable.level = 0





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
            //val newlevel = mImageDrawable.level + 5000
            //mImageDrawable.level = newlevel
            //mImageDrawable.invalidateSelf()


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


            }

        })

        fun doTheAnimation() {
            mLevel += 1000
            mImageDrawable.level = mLevel
            Log.d("MainActivity", "level: " + mLevel)
            if (mLevel <= 10000) {
                mHandler.postDelayed(animateImage, 500)
            } else {
                mHandler.removeCallbacks(animateImage)
            }
        }

        animateImage = Runnable { doTheAnimation() }

        fillCircleAnimator.duration = 2000
        secondAnimator.duration = 2000
        thirdAnimator.duration = 2000
        //fillCircleAnimator.repeatCount = ValueAnimator.INFINITE
        fillCircleAnimator.start()

        mHandler.post(animateImage)
        animateImage.run()
    }


}
