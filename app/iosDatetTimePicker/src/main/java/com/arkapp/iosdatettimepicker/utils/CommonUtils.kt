package com.arkapp.iosdatettimepicker.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Abdul Rehman on 01-04-2020.
 * Contact email - abdulrehman0796@gmail.com
 */

fun Window.setTransparentEdges() {
    decorView.setBackgroundResource(android.R.color.transparent)
}

fun Window.setFullWidth() {
    setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
}

fun RecyclerView.initVerticalAdapter(
    adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    hasFixedSize: Boolean
) {
    val llm = LinearLayoutManager(this.context)
    this.setHasFixedSize(hasFixedSize)
    this.setItemViewCacheSize(10)
    this.layoutManager = llm
    adapter.setHasStableIds(true)
    this.adapter = adapter
}

const val SLOW_SPEED = 100F
const val FAST_SPEED = 25F

@SuppressLint("SimpleDateFormat")
fun getDateFromCalendar(calendar: Calendar): String {
    val sdf = SimpleDateFormat("EEE dd MMM")
    return sdf.format(calendar.time)
}

fun getZeroPrefix(minute: Int): String {
    return if (minute < 10)
        "0$minute"
    else minute.toString()
}

fun getFormattedHour(isPM: Boolean, hour: Int): Int {
    return if (isPM)
        if (hour < 12)
            hour + 12
        else
            12
    else
        if (hour == 12)
            return 0
        else
            hour
}

fun Context.getSmoothScroll(millisecondsPerInch: Float): LinearSmoothScroller {
    //val millisecondsPerInch = 100f; //default is 25f (bigger = slower)

    return object : LinearSmoothScroller(this) {
        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }

        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
            return millisecondsPerInch / displayMetrics.densityDpi
        }

    }
}

fun RecyclerView.scrollToPositionTop(position: Int) {
    (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, 0)
}

fun smoothScrollToTop(rv: RecyclerView, position: Int, speed: Float) {
    val layoutManager = rv.layoutManager as LinearLayoutManager
    val smoothScroller = rv.context.getSmoothScroll(speed)
    smoothScroller.targetPosition = position
    layoutManager.startSmoothScroll(smoothScroller)
}

fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}

fun pxToDp(px: Int): Int {
    return (px / Resources.getSystem().displayMetrics.density).toInt()
}

fun spToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.scaledDensity).toInt()
}

fun pxToSp(px: Int): Int {
    return (px / Resources.getSystem().displayMetrics.scaledDensity).toInt()
}