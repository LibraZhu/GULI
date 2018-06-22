package com.libra.guli.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.widget.EditText
import com.libra.utils.dp2px


/**
 * @author Libra
 * @since 2018/6/22
 */
class LinedEditText : AppCompatEditText {
    constructor(context: Context) : super(context) {
        initPaint(); }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initPaint(); }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initPaint(); }

    var mPaint: Paint? = null
    private fun initPaint() {
        mPaint = Paint()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        mPaint?.style = Paint.Style.STROKE
        mPaint?.color = Color.GRAY
        val y = context.dp2px(4f).toFloat()
        val effects = DashPathEffect(floatArrayOf(y, y, y, y), 0f)
        mPaint?.pathEffect = effects

        val left = left
        val right = right
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom
        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val height = height
        val lineHeight = lineHeight
        val spcingHeight = lineSpacingExtra.toInt()
        val count = (height - paddingTop - paddingBottom) / lineHeight

        for (i in 0 until count + 1) {
            val baseline = lineHeight * (i + 1) + paddingTop - spcingHeight / 2
            canvas?.drawLine((paddingLeft).toFloat(), baseline.toFloat(), (right - paddingRight).toFloat(), baseline.toFloat(), mPaint)
        }
        super.onDraw(canvas)
    }



    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        var textString = text?.toString() ?: ""
        if (textString.endsWith("\n")) {
            textString = textString.plus("\n")
        }
        super.onTextChanged(textString, start, lengthBefore, lengthAfter)
        requestLayout()
    }
}
