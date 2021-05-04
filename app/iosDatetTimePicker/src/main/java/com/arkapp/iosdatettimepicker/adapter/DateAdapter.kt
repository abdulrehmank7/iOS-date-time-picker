package com.arkapp.iosdatettimepicker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arkapp.iosdatettimepicker.R
import com.arkapp.iosdatettimepicker.utils.DateViewHolder
import com.arkapp.iosdatettimepicker.utils.dpToPx
import com.arkapp.iosdatettimepicker.utils.getDateFromCalendar
import com.arkapp.iosdatettimepicker.utils.spToPx
import java.util.*

/**
 * Created by Abdul Rehman on 12-03-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
class DateAdapter(
    val dates: ArrayList<Calendar>,
    private val fontSize: Int,
    private val dividerHeight: Int
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = DateViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycler_date_seletion,
                parent,
                false
            )
        )
        view.binding.parent.layoutParams.height =
            dividerHeight.dpToPx(view.binding.tv.context).toInt()
        view.binding.tv.textSize = fontSize.spToPx(view.binding.tv.context)
        return view
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as DateViewHolder

        viewHolder.binding.tv.text = getDateFromCalendar(dates[position])
    }


    override fun getItemCount() = dates.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}
