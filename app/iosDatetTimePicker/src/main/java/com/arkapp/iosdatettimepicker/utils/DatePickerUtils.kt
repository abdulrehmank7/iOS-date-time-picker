package com.arkapp.iosdatettimepicker.utils

import androidx.recyclerview.widget.RecyclerView
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

    private val selectedDateUnvalidated: Calendar = Calendar.getInstance().also {
        it.time = startDate.time
    }
    val selectedDateTime: Calendar = Calendar.getInstance().also { it.time = startDate.time }
    var currentSelectedHour = 0
    var isPmSelectedUnvalidated = false

    init {
        isPmSelectedUnvalidated = startDate.get(Calendar.HOUR_OF_DAY) > 12
    }

    fun getAllDates(): ArrayList<Calendar> {

        val calendarStartDate = Calendar.getInstance().also { it.time = startDate.time }
        val calendarEndDate = Calendar.getInstance().also { it.time = endDate.time }
        val dates = ArrayList<Calendar>()

        calendarStartDate.add(Calendar.DAY_OF_YEAR, -(2 * INVISIBLE_VIEWS))
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
        list.add(2, -1)
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
        println("hour selected $hour")
        selectedDateUnvalidated.set(Calendar.HOUR_OF_DAY, hour)
    }

    fun setSelectedMinute(minute: Int) {
        selectedDateUnvalidated.set(Calendar.MINUTE, minute)
    }


    private fun getInitHourIndex(): Int {
        return if (startDate.get(Calendar.HOUR_OF_DAY) > 12)
            startDate.get(Calendar.HOUR_OF_DAY) - 12
        else
            startDate.get(Calendar.HOUR_OF_DAY)
    }

    private fun getInitMinuteIndex(): Int {
        return startDate.get(Calendar.MINUTE) + 1
    }

    private fun getInitMeridiemIndex(): Int {
        return if (startDate.get(Calendar.HOUR_OF_DAY) >= 12)
            1
        else
            0
    }


    fun resetDate(rv: RecyclerView, scrollSpeed: Float) {
        smoothScrollToTop(rv, 2, scrollSpeed)
    }

    fun resetHour(rv: RecyclerView, scrollSpeed: Float) {
        val index = getInitHourIndex()
        println("hour index $index")
        smoothScrollToTop(rv, index, scrollSpeed)
    }

    fun resetMinute(rv: RecyclerView, scrollSpeed: Float) {
        val index = getInitMinuteIndex()
        println("minute index $index")
        smoothScrollToTop(rv, index, scrollSpeed)
    }

    fun resetMeridiem(rv: RecyclerView, scrollSpeed: Float) {
        smoothScrollToTop(rv, getInitMeridiemIndex(), scrollSpeed)
    }

    fun setMinimumHour(rv: RecyclerView) {
        smoothScrollToTop(rv, 1, SLOW_SPEED)
    }

    fun setMinimumMinutes(rv: RecyclerView) {
        smoothScrollToTop(rv, 1, SLOW_SPEED)
    }

    fun isValidDate(): Boolean {
        println("${startDate.time} START_DATE")
        println("${selectedDateUnvalidated.time} SELECTED_DATE_BEFORE_VALIDATION ")
        return selectedDateUnvalidated.timeInMillis >= startDate.timeInMillis
    }
}