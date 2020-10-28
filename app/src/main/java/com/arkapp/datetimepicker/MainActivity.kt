package com.arkapp.datetimepicker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arkapp.iosdatettimepicker.utils.OnDateTimeSelectedListener
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
            val dateTimeSelectedListener = object :
                OnDateTimeSelectedListener {
                override fun onDateTimeSelected(selectedDateTime: Calendar) {
                    Toast.makeText(
                        this@MainActivity,
                        "Selected date ${selectedDateTime.time}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            val dialog = com.arkapp.iosdatettimepicker.ui.DialogDateTimePicker(
                this,
                startDate,
                12,
                dateTimeSelectedListener,
                "Select date and time"
            )

            dialog.setTitleTextColor(android.R.color.black)

            dialog.setDividerBgColor(android.R.color.black)

            dialog.setCancelBtnColor(R.color.colorAccent)
            dialog.setCancelBtnTextColor(R.color.colorPrimaryDark)

            dialog.setSubmitBtnColor(R.color.colorAccent)
            dialog.setSubmitBtnTextColor(R.color.colorPrimaryDark)

            dialog.setCancelBtnText("Dismiss")
            dialog.setSubmitBtnText("OK")

            dialog.show()
        }
    }
}
