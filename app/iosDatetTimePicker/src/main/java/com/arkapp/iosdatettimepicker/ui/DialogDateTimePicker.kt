package com.arkapp.iosdatettimepicker.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import androidx.annotation.Keep
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.arkapp.iosdatettimepicker.R
import com.arkapp.iosdatettimepicker.adapter.DateAdapter
import com.arkapp.iosdatettimepicker.adapter.HourAdapter
import com.arkapp.iosdatettimepicker.adapter.MeridiemAdapter
import com.arkapp.iosdatettimepicker.adapter.MinuteAdapter
import com.arkapp.iosdatettimepicker.databinding.DialogDateTimePickerBinding
import com.arkapp.iosdatettimepicker.utils.*
import java.util.*


/**
 * Created by Abdul Rehman on 5/16/2019.
 */
@Keep
class DialogDateTimePicker(
    context: Context,
    private val startDate: Calendar,
    private val maxMonthToDisplay: Int,
    private val dateTimeSelectedListener: OnDateTimeSelectedListener,
    private val title: String
) : Dialog(context, R.style.Theme_Custom_Dialog) {

    private lateinit var utils: DatePickerUtils
    private lateinit var dialogBinding: DialogDateTimePickerBinding

    private var titleTextColor = R.color.colorPrimaryDark

    private var centerViewBgColor = R.color.colorPrimaryDark

    private var cancelTextColor = android.R.color.white
    private var cancelBgColor = R.color.colorPrimaryDark

    private var submitTextColor = android.R.color.white
    private var submitBgColor = R.color.colorPrimaryDark

    private var cancelText = context.getString(R.string.cancel)
    private var submitText = context.getString(R.string.submit)

    private var fontSize: Int = 14

    private var dividerHeight: Int = 38

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
        window?.setGravity(Gravity.BOTTOM)


        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(dialogBinding.root)

        window?.setTransparentEdges()
        window?.setFullWidth()

        utils = DatePickerUtils(startDate, endDate)

        dialogBinding.title.text = title

        dialogBinding.title.setTextColor(ContextCompat.getColor(context, titleTextColor))
        dialogBinding.submitBtn.setTextColor(ContextCompat.getColor(context, submitTextColor))
        dialogBinding.cancelBtn.setTextColor(ContextCompat.getColor(context, cancelTextColor))

        dialogBinding.viewCenter.setBackgroundColor(
            ContextCompat.getColor(
                context,
                centerViewBgColor
            )
        )

        dialogBinding.submitBtn.setBackgroundColor(ContextCompat.getColor(context, submitBgColor))
        dialogBinding.cancelBtn.setBackgroundColor(ContextCompat.getColor(context, cancelBgColor))

        dialogBinding.submitBtn.text = submitText
        dialogBinding.cancelBtn.text = cancelText

        dialogBinding.viewCenter.layoutParams.height = dividerHeight.dpToPx(context).toInt()

        val dateAdapter = DateAdapter(utils.getAllDates(), fontSize, dividerHeight)
        val hourAdapter = HourAdapter(
            utils.addEmptyValue(
                utils.getHours(false)
            ),
            fontSize,
            dividerHeight
        )
        val meridiemAdapter =
            MeridiemAdapter(
                utils.addEmptyValueInString(utils.getMeridiem()),
                fontSize,
                dividerHeight
            )
        val minuteAdapter =
            MinuteAdapter(
                utils.addEmptyValue(utils.getMinutes()),
                fontSize,
                dividerHeight
            )

        dialogBinding.dateRv.initVerticalAdapter(dateAdapter, true)
        dialogBinding.hourRv.initVerticalAdapter(hourAdapter, true)
        dialogBinding.meridiemRv.initVerticalAdapter(meridiemAdapter, true)
        dialogBinding.minuteRv.initVerticalAdapter(minuteAdapter, true)

        val dateSnapListener = object : CustomSnapHelper.SnapListener {
            override fun onViewSnapped(position: Int) {
                utils.setSelectedDate(
                    dateAdapter.dates[position].get(Calendar.DAY_OF_YEAR),
                    dateAdapter.dates[position].get(Calendar.YEAR)
                )

                validateDateTime()
            }
        }

        val hourSnapListener = object : CustomSnapHelper.SnapListener {
            override fun onViewSnapped(position: Int) {
                println("hour snapped position $position")
                if (position >= 3) {
                    utils.currentSelectedHour = hourAdapter.hour[position]
                    println("current selected hour ${utils.currentSelectedHour}")
                    utils.setSelectedHour(
                        getFormattedHour(
                            utils.isPmSelectedUnvalidated,
                            hourAdapter.hour[position]
                        )
                    )
                    validateDateTime()
                } else
                    utils.setMinimumHour(dialogBinding.hourRv)
            }
        }

        val minuteSnapListener = object : CustomSnapHelper.SnapListener {
            override fun onViewSnapped(position: Int) {
                if (position >= 3) {
                    utils.setSelectedMinute(minuteAdapter.minute[position])
                    validateDateTime()
                } else
                    utils.setMinimumMinutes(dialogBinding.minuteRv)
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

    fun setTitleTextColor(color: Int) {
        titleTextColor = color
    }

    fun setDividerBgColor(color: Int) {
        centerViewBgColor = color
    }

    fun setSubmitBtnColor(color: Int) {
        submitBgColor = color
    }

    fun setSubmitBtnTextColor(color: Int) {
        submitTextColor = color
    }

    fun setCancelBtnColor(color: Int) {
        cancelBgColor = color
    }

    fun setCancelBtnTextColor(color: Int) {
        cancelTextColor = color
    }

    fun setSubmitBtnText(submitTxt: String) {
        submitText = submitTxt
    }

    fun setCancelBtnText(cancelTxt: String) {
        cancelText = cancelTxt
    }

    fun setFontSize(sizeInSp: Int) {
        fontSize = sizeInSp
    }

    fun setCenterDividerHeight(sizeInDp: Int) {
        dividerHeight = sizeInDp
    }

}