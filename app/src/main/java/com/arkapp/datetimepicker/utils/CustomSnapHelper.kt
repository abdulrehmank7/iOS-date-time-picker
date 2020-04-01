package com.arkapp.datetimepicker.utils

import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Abdul Rehman on 31-03-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
class CustomSnapHelper(
    private val rv: RecyclerView,
    private val snapListener: SnapListener
) : LinearSnapHelper() {

    init {
        this.attachToRecyclerView(rv)
    }

    private var selectedPosition = -1

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        val view = super.findSnapView(layoutManager)

        if (view != null) {
            val newPosition = layoutManager!!.getPosition(view)
            if (newPosition != selectedPosition && rv.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                snapListener.onViewSnapped(newPosition)
                selectedPosition = newPosition
            }
        }

        return view
    }


    interface SnapListener {
        fun onViewSnapped(position: Int)
    }
}