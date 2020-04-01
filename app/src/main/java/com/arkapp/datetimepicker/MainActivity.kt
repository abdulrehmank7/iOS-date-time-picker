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

        val startDate: Calendar = Calendar.getInstance()
        val dateTimeSelectedListener = object : OnDateTimeSelectedListener {
            override fun onDateTimeSelected(selectedDateTime: Calendar) {
            }
        }

        val dialog = DialogDateTimePicker(this, startDate, 12, dateTimeSelectedListener)

        showDateTimePicker.setOnClickListener {
            dialog.show()
        }
    }
}
