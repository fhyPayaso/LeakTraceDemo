package com.bytedance.fanhongyu.leaktrace.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.text.style.ReplacementSpan

/**
 *
 * @author fhyPayaso
 * @since 2019/3/6 9:22 PM
 */
class TestSpan private constructor(val builder: Builder) : ReplacementSpan() {


    private var backgroundDrawable: Drawable? = null
    private var iconDrawable: Drawable? = null
    private var iconWidth: Int = 0
    private var iconHeight: Int = 0

    private var paddingLeft: Int = 0
    private var paddingRight: Int = 0
    private var paddingTop: Int = 0
    private var paddingBottom: Int = 0

    private var textColor: Int = Color.WHITE

    private var totalWidth: Int = 0

    init {
        backgroundDrawable = builder.backgroundDrawable
        iconDrawable = builder.iconDrawable
        iconWidth = builder.iconWidth
        iconHeight = builder.iconHeight
        paddingLeft = builder.paddingLeft
        paddingRight = builder.paddingRight
        paddingTop = builder.paddingTop
        paddingBottom = builder.paddingBottom
    }

    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        totalWidth = paint.measureText(text, start, end).toInt()
        totalWidth += (paddingLeft + paddingRight + iconWidth)

        return totalWidth
    }


    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int, // span的起始位置
        end: Int, // span的终点位置
        x: Float,
        top: Int, // 该行文字显示区域的top值
        y: Int, // 字体的baseline值
        bottom: Int, // 该行文字显示区域的bottom值
        paint: Paint
    ) {

        val fm = paint.fontMetricsInt

        var left = x.toInt()

        var totalHeight = y + fm.descent - top

        // 绘制背景
        backgroundDrawable?.setBounds(left, top - paddingTop, left + totalWidth, top + totalHeight + paddingBottom)
        backgroundDrawable?.draw(canvas)

        // 绘制icon
        left += paddingLeft
        val iconTop = top + (totalHeight - iconHeight) / 2
        iconDrawable?.setBounds(left, iconTop, left + iconWidth, iconTop + iconHeight)
        iconDrawable?.draw(canvas)
        left += iconWidth

        // 绘制文字
        val textHeight = fm.descent - fm.ascent
        paint.color = textColor
        val yy = (top + totalHeight - (totalHeight - textHeight) / 2 - fm.descent).toFloat()
        canvas.drawText(text!!, start, end, left.toFloat(), yy, paint)


    }


    class Builder(var context: Context) {

        var backgroundDrawable: Drawable? = null
        var iconDrawable: Drawable? = null
        var iconWidth: Int = 0
        var iconHeight: Int = 0

        var paddingLeft: Int = 0
        var paddingRight: Int = 0
        var paddingTop: Int = 0
        var paddingBottom: Int = 0

        var textColor: Int = Color.WHITE

        var totalWidth: Int = 0

        fun setBackground(@DrawableRes drawableRes: Int): Builder {
            val drawable = context.resources.getDrawable(drawableRes)
            this.backgroundDrawable = drawable
            return this
        }

        fun setIcon(@DrawableRes drawableRes: Int): Builder {
            val drawable = context.resources.getDrawable(drawableRes)
            this.iconDrawable = drawable
            return this
        }

        fun setIWidth(width: Int): Builder {
            this.iconWidth = dip2Px(width)
            return this
        }

        fun setIHeight(height: Int): Builder {
            this.iconHeight = dip2Px(height)
            return this
        }

        fun setPLeft(padding: Int): Builder {
            this.paddingLeft = dip2Px(padding)
            return this
        }

        fun setPTop(padding: Int): Builder {
            this.paddingTop = dip2Px(padding)
            return this
        }

        fun setPRight(padding: Int): Builder {
            this.paddingRight = dip2Px(padding)
            return this
        }

        fun setPBottom(padding: Int): Builder {
            this.paddingBottom = dip2Px(padding)
            return this
        }

        fun setTColor(@ColorRes colorRes: Int): Builder {
            this.textColor = context.resources.getColor(colorRes)
            return this
        }

        fun build(): TestSpan {
            return TestSpan(this)
        }

        fun dip2Px(dipValue: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (dipValue * scale + 0.5f).toInt()
        }

    }

}

