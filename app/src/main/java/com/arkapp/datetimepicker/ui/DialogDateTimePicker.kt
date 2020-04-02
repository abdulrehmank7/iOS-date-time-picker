package com.arkapp.datetimepicker.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.recyclerview.widget.RecyclerView
import com.arkapp.datetimepicker.adapter.DateAdapter
import com.arkapp.datetimepicker.adapter.HourAdapter
import com.arkapp.datetimepicker.adapter.MeridiemAdapter
import com.arkapp.datetimepicker.adapter.MinuteAdapter
import com.arkapp.datetimepicker.databinding.DialogDateTimePickerBinding
import com.arkapp.datetimepicker.utils.*
import java.util.*


/**
 * Created by Abdul Rehman on 5/16/2019.
 */
class DialogDateTimePicker(
    context: Context,
    private val startDate: Calendar,
    private val maxMonthToDisplay: Int,
    private val dateTimeSelectedListener: OnDateTimeSelectedListener
) : Dialog(context) {

    private lateinit var utils: DatePickerUtils
    private lateinit var dialogBinding: DialogDateTimePickerBinding
    private var endDate: Calendar = Calendar.getInstance().also {
        it.timeInMillis = startDate.timeInMillis
        it.add(Calendar.MONTH, maxMonthToDisplay)
    }

    init {
        setOnShowListener { initDates(FAST_SPEED) }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialogBinding = DialogDateTimePickerBinding.inflate(LayoutInflater.from(context))

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(dialogBinding.root)

        window?.setTransparentEdges()
        window?.setFullWidth()

        utils = DatePickerUtils(startDate, endDate)

        val dateAdapter = DateAdapter(utils.getAllDates())
        val hourAdapter = HourAdapter(utils.addEmptyValue(utils.getHours(false)))
        val meridiemAdapter = MeridiemAdapter(utils.addEmptyValueInString(utils.getMeridiem()))
        val minuteAdapter = MinuteAdapter(utils.addEmptyValue(utils.getMinutes()))

        dialogBinding.dateRv.initVerticalAdapter(dateAdapter, true)
        dialogBinding.hourRv.initVerticalAdapter(hourAdapter, true)
        dialogBinding.meridiemRv.initVerticalAdapter(meridiemAdapter, true)
        dialogBinding.minuteRv.initVerticalAdapter(minuteAdapter, true)

        val dateSnapListener = object : CustomSnapHelper.SnapListener {
            override fun onViewSnapped(position: Int) {
                utils.setSelectedDate(dateAdapter.dates[position].get(Calendar.DAY_OF_YEAR))

                validateDateTime()
            }
        }

        val hourSnapListener = object : CustomSnapHelper.SnapListener {
            override fun onViewSnapped(position: Int) {
                utils.currentSelectedHour = hourAdapter.hour[position]
                utils.setSelectedHour(
                    getFormattedHour(
                        utils.isPmSelectedUnvalidated,
                        hourAdapter.hour[position]
                    )
                )
                validateDateTime()
            }
        }

        val minuteSnapListener = object : CustomSnapHelper.SnapListener {
            override fun onViewSnapped(position: Int) {
                utils.setSelectedMinute(minuteAdapter.minute[position])
                validateDateTime()
            }
        }

        val meridiemSnapListener = object : CustomSnapHelper.SnapListener {
            override fun onViewSnapped(position: Int) {
                utils.isPmSelectedUnvalidated = meridiemAdapter.meridiem[position] == PM
                utils.setSelectedHour(
                    getFormattedHour(
                        utils.isPmSelectedUnvalidated,
                        utils.currentSelectedHour
                    )
                )
                validateDateTime()
            }
        }

        CustomSnapHelper(dialogBinding.dateRv, dateSnapListener)
        CustomSnapHelper(dialogBinding.hourRv, hourSnapListener)
        CustomSnapHelper(dialogBinding.minuteRv, minuteSnapListener)
        CustomSnapHelper(dialogBinding.meridiemRv, meridiemSnapListener)

        dialogBinding.submitBtn.setOnClickListener {
            dateTimeSelectedListener.onDateTimeSelected(utils.selectedDateTime)
            dismiss()
        }

        dialogBinding.cancelBtn.setOnClickListener { dismiss() }
    }

    private fun initDates(scrollSpeed: Float) {
        utils.resetDate(dialogBinding.dateRv, scrollSpeed)
        utils.resetMeridiem(dialogBinding.meridiemRv, scrollSpeed)
        utils.resetHour(dialogBinding.hourRv, scrollSpeed)
        utils.resetMinute(dialogBinding.minuteRv, scrollSpeed)
    }


    private fun isStoppedScrolling(): Boolean {
        return dialogBinding.dateRv.scrollState == RecyclerView.SCROLL_STATE_IDLE &&
                dialogBinding.hourRv.scrollState == RecyclerView.SCROLL_STATE_IDLE &&
                dialogBinding.minuteRv.scrollState == RecyclerView.SCROLL_STATE_IDLE &&
                dialogBinding.meridiemRv.scrollState == RecyclerView.SCROLL_STATE_IDLE
    }

    private fun validateDateTime() {
        if (isStoppedScrolling()) {
            if (utils.isValidDate())
                utils.setSelectedDateTime()
            else
                initDates(SLOW_SPEED)
        }
    }

}