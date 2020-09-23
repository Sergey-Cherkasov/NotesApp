package br.svcdev.notesapp.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.Dimension.DP
import androidx.annotation.Dimension.PX
import androidx.core.content.ContextCompat
import br.svcdev.notesapp.R
import br.svcdev.notesapp.common.extensions.dip

class ColorCircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                                defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    companion object {
        @Dimension(unit = DP) private const val defRadiusDp = 16
        @Dimension(unit = DP) private const val defStrokeWidthDp = 1
    }

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private var center: Pair<Float, Float> = 0f to 0f

    @Dimension(unit = PX) var radius: Float = dip(defRadiusDp)

    @Dimension(unit = PX) var strokeWidth: Float = dip(defStrokeWidthDp)
        set(value) {
            field = value
            strokePaint.strokeWidth = value
        }

    @ColorRes var fillColorRes: Int = R.color.colorWhite
        set(value) {
            field = value
            fillPaint.color = ContextCompat.getColor(context, value)
        }
    @ColorRes var strokeColorRes: Int = R.color.colorSecondary
        set(value) {
            field = value
            strokePaint.color = ContextCompat.getColor(context, value)
        }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorCircleView)
        val defRadiusPx = dip(defRadiusDp)

        radius = typedArray.getDimension(R.styleable.ColorCircleView_circleRadius, defRadiusPx)

        fillColorRes = typedArray.getResourceId(R.styleable.ColorCircleView_fillColor,
            R.color.colorWhite)

        val defStrokeWidthPx = dip(defStrokeWidthDp)

        strokeWidth = typedArray.getDimension(R.styleable.ColorCircleView_strokeWidth,
            defStrokeWidthPx)

        strokeColorRes = typedArray.getResourceId(R.styleable.ColorCircleView_strokeColor,
            R.color.colorSecondary)

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = (radius * 2 + paddingTop + paddingBottom).toInt()
        val width = (radius * 2 + paddingStart + paddingEnd).toInt()
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        center = measuredWidth / 2f to measuredHeight / 2f
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.let {
            it.drawCircle(center.first, center.second, radius, strokePaint)
            it.drawCircle(center.first, center.second, radius - strokeWidth, fillPaint)
        }
    }
}