package com.arkapp.datetimepicker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arkapp.datetimepicker.ui.DialogDateTimePicker
import com.arkapp.datetimepicker.utils.OnDateTimeSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showDateTimePicker.setOnClickListener {

            val startDate: Calendar = Calendar.getInstance()/*.also {
                it.set(Calendar.DAY_OF_MONTH, 21)
                it.set(Calendar.HOUR_OF_DAY, 13)
                it.set(Calendar.MINUTE, 0)
            }*/
            val dateTimeSelectedListener = object : OnDateTimeSelectedListener {
                override fun onDateTimeSelected(selectedDateTime: Calendar) {
                    println("Selected date ${selectedDateTime.time}")
                }
            }

            val dialog = DialogDateTimePicker(
                this,
                startDate,
                12,
                dateTimeSelectedListener,
                "Select date and time"
            )

            dialog.show()
        }
    }
}
