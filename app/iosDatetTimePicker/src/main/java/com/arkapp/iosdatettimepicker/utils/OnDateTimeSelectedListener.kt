package com.arkapp.iosdatettimepicker.utils

import androidx.annotation.Keep
import java.util.*

/**
 * Created by Abdul Rehman on 01-04-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
@Keep
interface OnDateTimeSelectedListener {
    fun onDateTimeSelected(selectedDateTime: Calendar)
}