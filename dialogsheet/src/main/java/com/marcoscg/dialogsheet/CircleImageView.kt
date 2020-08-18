package com.marcoscg.dialogsheet

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

/**
 * A simple [ImageView] that crops to a circle shape.
 * Source: https://gist.github.com/mobiRic/b390545ad3ddcafd9fb7206cef0d4a44
 */
class CircleImageView : AppCompatImageView {

    companion object {
        private const val DEFAULT_FRAME_WIDTH_DP = 3 /*dp*/

        @ColorInt
        private val DEFAULT_FRAME_COLOR = Color.WHITE
    }

    private var framePaint: Paint? = null
    private var frameWidth = 0f
    private var x = 0
    private var y = 0
    private var radius = 0f
    private var frameClip: Path? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        val scale = resources.displayMetrics.density
        frameWidth = (DEFAULT_FRAME_WIDTH_DP * scale + 0.5f)
        framePaint = Paint()
        framePaint?.isAntiAlias = true
        framePaint?.color = DEFAULT_FRAME_COLOR
        framePaint?.style = Paint.Style.STROKE
        framePaint?.strokeWidth = frameWidth
        frameClip = Path()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // check if anything has changed
        if (measuredWidth / 2 == x && measuredHeight / 2 == y) {
            return
        }
        x = measuredWidth / 2
        y = measuredHeight / 2
        radius = min(x, y) - frameWidth / 2
        frameClip?.reset()
        frameClip?.addCircle(
                x.toFloat(), y.toFloat(),
                radius,
                Path.Direction.CW)
        frameClip?.close()
    }

    override fun onDraw(canvas: Canvas) {

        // circle crop the canvas
        val save = canvas.save()
        frameClip?.let {
            canvas.clipPath(it)
        }
        run { super.onDraw(canvas) }
        canvas.restoreToCount(save)

        // cropping does not anti-alias so mask with a frame
        framePaint?.let {
            canvas.drawCircle(
                    x.toFloat(), y.toFloat(),
                    radius,
                    it)
        }
    }
}