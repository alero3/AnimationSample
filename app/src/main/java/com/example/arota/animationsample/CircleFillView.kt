package com.example.arota.animationsample

import android.R.attr.y
import android.R.attr.x
import android.content.Context
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import com.example.arota.animationsample.CircleFillView
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.graphics.BitmapFactory
import android.graphics.Bitmap




/**
 * Created by Reply on 23/08/2018.
 */
class CircleFillView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val MIN_VALUE = 0
    val MAX_VALUE = 100

    val center = PointF(720f, 1120f)
    private val circleRect = RectF()
    private val segment = Path()
    private val strokePaint = Paint()
    private val fillPaint = Paint()

    var radius: Float = 700f

    // Custom values to be set in layout.xml
    private var fillColor: Int = 0
    private var strokeColor: Int = 0
    private var strokeWidth: Float = 0.toFloat()
    var fillHeight: Int = 0
    lateinit var p: Paint

    // Gas pump image
    val gasPumpBitmap: Bitmap

    init {

        gasPumpBitmap = BitmapFactory.decodeResource(resources, R.drawable.gas_pump_black)

        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.CircleFillView,
                0, 0)

        try {
            fillColor = a.getColor(R.styleable.CircleFillView_fillColor, Color.WHITE)
            strokeColor = a.getColor(R.styleable.CircleFillView_strokeColor, Color.BLACK)
            strokeWidth = a.getFloat(R.styleable.CircleFillView_strokeWidth, 1f)
            fillHeight = a.getInteger(R.styleable.CircleFillView_value, 0)
            adjustValue(fillHeight)
        } finally {
            a.recycle()
        }

        fillPaint.color = fillColor
        strokePaint.color = strokeColor
        strokePaint.strokeWidth = strokeWidth
        strokePaint.style = Paint.Style.STROKE

    }



    fun setValue(value: Int) {
        adjustValue(value)
        setPaths()

        invalidate()
    }

    fun getValue(): Int {
        return fillHeight
    }

    private fun adjustValue(value: Int) {
        this.fillHeight = Math.min(MAX_VALUE, Math.max(MIN_VALUE, value))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        center.x = (width / 2).toFloat()
        center.y = (height / 2).toFloat()
        radius = ((Math.min(width, height) / 2 - strokeWidth.toInt())).toFloat()
        circleRect.set(center.x - radius, center.y - radius, center.x + radius, center.y + radius)

        setPaths()
    }

    fun getWaterLevel (fillHeight: Int) : Float {
        val y = center.y + radius - (2 * radius * fillHeight / 100 - 1)
        return y
    }

    private fun setPaths() {
        val y = center.y + radius - (2 * radius * fillHeight / 100 - 1)
        val x = center.x - Math.sqrt(Math.pow(radius.toDouble(), 2.0) - Math.pow((y - center.y).toDouble(), 2.0)).toFloat()

        val angle = Math.toDegrees(Math.atan(((center.y - y) / (x - center.x)).toDouble())).toFloat()
        val startAngle = 180 - angle
        val sweepAngle = 2 * angle - 180

        segment.rewind()
        segment.addArc(circleRect, startAngle, sweepAngle)
        segment.close()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPath(segment, fillPaint)
        canvas.drawCircle(center.x, center.y, radius, strokePaint)
        p = Paint()

        //canvas.drawBitmap(gasPumpBitmap, center.x - (gasPumpBitmap.width/2), center.y - (gasPumpBitmap.height/2), p)
    }

    fun getPumpBitmapBottom() : Float {
        return center.y + (gasPumpBitmap.height/2)
    }

    fun getPumpBitmapTop() : Float {
        return center.y - (gasPumpBitmap.height/2)
    }
}