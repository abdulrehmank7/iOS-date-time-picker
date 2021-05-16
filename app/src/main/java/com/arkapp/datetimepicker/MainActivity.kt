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

            val startDate: Calendar = Calendar.getInstance()
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

            dialog.setTitleTextColor(R.color.colorPrimaryDark)

            dialog.setDividerBgColor(R.color.colorAccent)

            dialog.setCancelBtnColor(R.color.colorPrimaryDark)
            dialog.setCancelBtnTextColor(R.color.colorAccent)

            dialog.setSubmitBtnColor(R.color.colorPrimaryDark)
            dialog.setSubmitBtnTextColor(R.color.colorAccent)

            dialog.setCancelBtnText("Cancel")
            dialog.setSubmitBtnText("Submit")
            dialog.setFontSize(18)
            dialog.setCenterDividerHeight(48)

            dialog.show()
        }
    }
}
