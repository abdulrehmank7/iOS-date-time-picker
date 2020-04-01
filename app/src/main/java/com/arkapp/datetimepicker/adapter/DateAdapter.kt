package com.arkapp.datetimepicker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arkapp.datetimepicker.R
import com.arkapp.datetimepicker.utils.DateViewHolder
import com.arkapp.datetimepicker.utils.getDateFromCalendar
import java.util.*

/**
 * Created by Abdul Rehman on 12-03-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
class DateAdapter(val dates: ArrayList<Calendar>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DateViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycler_date_seletion,
                parent,
                false
            )
        )
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
