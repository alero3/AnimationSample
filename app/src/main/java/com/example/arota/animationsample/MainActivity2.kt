package com.example.arota.animationsample

import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.animation.ObjectAnimator



/**
 * Created by Reply on 24/09/2018.
 */
class MainActivity2 : AppCompatActivity() {

    private lateinit var mImageDrawable: ClipDrawable

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main2)

        val img = findViewById<ImageView>(R.id.pump_full_iv)
        mImageDrawable = ClipDrawable(img.drawable, Gravity.BOTTOM, ClipDrawable.VERTICAL)
        mImageDrawable.level = 5000


        img.setImageDrawable(mImageDrawable)


        val anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000)
        anim.duration = 4000
        anim.repeatCount = ObjectAnimator.INFINITE
        anim.start()



    }
}