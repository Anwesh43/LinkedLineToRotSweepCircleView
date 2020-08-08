package com.anwesh.uiprojects.linetorotsweepcircleview

/**
 * Created by anweshmishra on 08/08/20.
 */

import android.view.View
import android.view.MotionEvent
import android.content.Context
import android.app.Activity
import android.graphics.Paint
import android.graphics.Color
import android.graphics.Canvas
import android.graphics.RectF

val colors : Array<String> = arrayOf("", "", "", "", "")
val sizeFactor : Float = 5.8f
val strokeFactor : Float = 90f
val delay : Long = 20
val backColor : Int = Color.parseColor("#BDBDBD")
val parts : Int = 4
val lineDeg : Float = 90f
val sweepDeg : Float = 360f
val startDeg : Float = -90f

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawLineToRotateSweepCircle(scale : Float, w : Float, h : Float, paint : Paint) {
    val size : Float = Math.min(w, h) / sizeFactor
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    val sf3 : Float = sf.divideScale(2, parts)
    val sf4 : Float = sf.divideScale(3, parts)
    save()
    translate(size + (w / 2 - size) * sf2, h / 2)
    if (sf4 <= 0f) {
        save()
        rotate(lineDeg * sf3)
        drawLine(-size, 0f, -size + size * sf1, 0f, paint)
        restore()
    }
    drawArc(RectF(-size, -size, size, size), startDeg, sweepDeg * sf4, true, paint)
    restore()
}

fun Canvas.drawLTRSNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = Color.parseColor(colors[i])
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawLineToRotateSweepCircle(scale, w, h, paint)
}
