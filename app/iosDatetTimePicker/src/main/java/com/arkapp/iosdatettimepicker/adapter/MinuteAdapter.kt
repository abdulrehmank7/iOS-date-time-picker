package com.arkapp.iosdatettimepicker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arkapp.iosdatettimepicker.R
import com.arkapp.iosdatettimepicker.utils.DateViewHolder
import com.arkapp.iosdatettimepicker.utils.getZeroPrefix
import java.util.*

/**
 * Created by Abdul Rehman on 12-03-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
class MinuteAdapter(val minute: ArrayList<Int>) :
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

        if (minute[position] != -1)
            viewHolder.binding.tv.text = getZeroPrefix(minute[position])
        else
            viewHolder.binding.tv.text = ""
    }


    override fun getItemCount() = minute.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}
