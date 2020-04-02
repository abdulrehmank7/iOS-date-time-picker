package com.arkapp.datetimepicker.utils

import android.view.View
import android.widget.TextView
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

    private var lastSelectedView: TextView? = null

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        val view = super.findSnapView(layoutManager)

        if (view != null) {
            val newPosition = layoutManager!!.getPosition(view)
            if (newPosition != selectedPosition && rv.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                selectedPosition = newPosition
                snapListener.onViewSnapped(newPosition)

                setFont(newPosition)
            }
        }

        return view
    }

    private fun setFont(newPosition: Int) {
        if (lastSelectedView != null)
        //Utility.setTypefaceMontserratMedium(rv.context, lastSelectedView)

            lastSelectedView =
                (rv.findViewHolderForAdapterPosition(newPosition) as DateViewHolder).binding.tv
        //Utility.setTypefaceMontserratBold(rv.context, lastSelectedView)
    }


    interface SnapListener {
        fun onViewSnapped(position: Int)
    }
}