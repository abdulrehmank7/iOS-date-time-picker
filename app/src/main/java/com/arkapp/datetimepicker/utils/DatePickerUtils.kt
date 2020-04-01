package com.arkapp.datetimepicker.utils

import androidx.recyclerview.widget.RecyclerView
import com.arkapp.datetimepicker.adapter.HourAdapter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


/**
 * Created by Abdul Rehman on 31-03-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
const val INVISIBLE_VIEWS = 2
const val AM = "AM"
const val PM = "PM"

class DatePickerUtils(private val startDate: Calendar, private val endDate: Calendar) {

    private val selectedDateUnvalidated: Calendar = Calendar.getInstance()
    private val selectedDateTime: Calendar = Calendar.getInstance()
    var currentSelectedHour = 0
    var isPmSelectedUnvalidated = false

    init {
        isPmSelectedUnvalidated = startDate.get(Calendar.HOUR_OF_DAY) >= 12
    }

    fun getAllDates(): ArrayList<Calendar> {

        val calendarStartDate = Calendar.getInstance().also { it.time = startDate.time }
        val calendarEndDate = Calendar.getInstance().also { it.time = endDate.time }
        val dates = ArrayList<Calendar>()

        calendarStartDate.add(Calendar.DAY_OF_YEAR, -INVISIBLE_VIEWS)
        calendarEndDate.add(Calendar.DAY_OF_YEAR, INVISIBLE_VIEWS)

        val totalDaysBetweenEnds =
            TimeUnit.MILLISECONDS.toDays(calendarEndDate.timeInMillis - calendarStartDate.timeInMillis)
                .toInt()

        for (count in 0..totalDaysBetweenEnds) {
            val date =
                Calendar.getInstance().also { it.timeInMillis = calendarStartDate.timeInMillis }
            dates.add(date.also { it.add(Calendar.DAY_OF_YEAR, count) })
        }

        return dates
    }

    fun getHours(is24Hour: Boolean): ArrayList<Int> {

        val hours = ArrayList<Int>()
        if (is24Hour) {
            for (hour in 0..23) hours.add(hour)
        } else {
            for (hour in 1..12) hours.add(hour)
        }

        return hours
    }

    fun getMinutes(): ArrayList<Int> {
        val minutes = ArrayList<Int>()
        for (hour in 0..59) minutes.add(hour)
        return minutes
    }

    fun getMeridiem(): ArrayList<String> {
        val meridiem = ArrayList<String>()
        meridiem.add(AM)
        meridiem.add(PM)
        return meridiem
    }


    fun addEmptyValue(list: ArrayList<Int>): ArrayList<Int> {
        list.add(0, -1)
        list.add(1, -1)
        list.add(-1)
        list.add(-1)
        return list
    }

    fun addEmptyValueInString(list: ArrayList<String>): ArrayList<String> {
        list.add(0, "")
        list.add(1, "")
        list.add("")
        list.add("")
        return list
    }


    fun setSelectedDateTime() {
        selectedDateTime.timeInMillis = selectedDateUnvalidated.timeInMillis
    }

    fun setSelectedDate(dayOfYear: Int) {
        selectedDateUnvalidated.set(Calendar.DAY_OF_YEAR, dayOfYear)
    }

    fun setSelectedHour(hour: Int) {
        selectedDateUnvalidated.set(Calendar.HOUR_OF_DAY, hour)
    }

    fun setSelectedMinute(minute: Int) {
        selectedDateUnvalidated.set(Calendar.MINUTE, minute)
    }


    fun resetDate(rv: RecyclerView, isSmoothScroll: Boolean) {
        if (isSmoothScroll)
            smoothScrollToTop(rv, 0)
        else
            rv.scrollToPositionTop(0)
    }

    fun resetHour(rv: RecyclerView, isSmoothScroll: Boolean) {
        val startHour =
            if (startDate.get(Calendar.HOUR_OF_DAY) > 12)
                startDate.get(Calendar.HOUR_OF_DAY) - 12
            else
                startDate.get(Calendar.HOUR_OF_DAY)


        (rv.adapter as HourAdapter).hour.forEachIndexed { index, i ->
            if (i == startHour) {
                if (isSmoothScroll)
                    smoothScrollToTop(
                        rv,
                        index - INVISIBLE_VIEWS
                    )
                else
                    rv.scrollToPositionTop(index - INVISIBLE_VIEWS)
            }
        }
    }

    fun resetMinute(rv: RecyclerView, isSmoothScroll: Boolean) {
        if (isSmoothScroll)
            smoothScrollToTop(
                rv,
                startDate.get(Calendar.MINUTE)
            )
        else
            rv.scrollToPositionTop(startDate.get(Calendar.MINUTE))
    }

    fun resetMeridiem(rv: RecyclerView, isSmoothScroll: Boolean) {
        if (startDate.get(Calendar.HOUR_OF_DAY) >= 12) {
            if (isSmoothScroll)
                smoothScrollToTop(rv, 1)
            else
                rv.scrollToPositionTop(1)
        } else {
            if (isSmoothScroll)
                smoothScrollToTop(rv, 0)
            else
                rv.scrollToPositionTop(0)
        }
    }


    fun isValidDate(): Boolean {
        println("SELECTED_DATE_BEFORE_VALIDATION ${selectedDateUnvalidated.time}")
        println("START_DATE ${startDate.time}")
        return selectedDateUnvalidated.timeInMillis >= startDate.timeInMillis
    }
}