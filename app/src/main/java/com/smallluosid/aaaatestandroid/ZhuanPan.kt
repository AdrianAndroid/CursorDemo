package com.smallluosid.aaaatestandroid

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import kotlin.math.min

class ZhuanPan @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var centerX = 0f
    private var centerY = 0f
    private var radius = 0f
    private var rotationAngle = 0f
    
    // 转盘项目
    private val items = listOf(
        "乾", "兑", "离", "震",
        "巽", "坎", "艮", "坤"
    )
    
    private val colors = listOf(
        Color.parseColor("#FF4444"),
        Color.parseColor("#FFFFFF"),
        Color.parseColor("#FF4444"),
        Color.parseColor("#FFFFFF"),
        Color.parseColor("#FF4444"),
        Color.parseColor("#FFFFFF"),
        Color.parseColor("#FF4444"),
        Color.parseColor("#FFFFFF")
    )

    init {
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2f
        centerY = h / 2f
        radius = min(w, h) / 2f * 0.8f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        // 绘制外圆
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        paint.color = Color.BLACK
        canvas.drawCircle(centerX, centerY, radius, paint)

        // 绘制分隔线和文字
        val sweepAngle = 360f / items.size
        paint.textSize = radius * 0.2f
        
        canvas.save()
        canvas.rotate(rotationAngle, centerX, centerY)
        
        for (i in items.indices) {
            // 绘制扇形
            paint.style = Paint.Style.FILL
            paint.color = colors[i]
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                i * sweepAngle,
                sweepAngle,
                true,
                paint
            )

            // 绘制文字
            paint.color = Color.BLACK
            canvas.save()
            canvas.rotate(i * sweepAngle + sweepAngle / 2, centerX, centerY)
            canvas.drawText(
                items[i],
                centerX,
                centerY - radius * 0.7f,
                paint
            )
            canvas.restore()
        }
        canvas.restore()
    }

    fun startRotation() {
        val animator = ValueAnimator.ofFloat(0f, 360f * 5 + (Math.random() * 360).toFloat())
        animator.duration = 3000
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addUpdateListener { animation ->
            rotationAngle = animation.animatedValue as Float
            invalidate()
        }
        animator.start()
    }
}
