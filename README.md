# IOS-Date-Time-Picker
[![](https://jitpack.io/v/blessedCode07/iOS-date-time-picker.svg)](https://jitpack.io/#blessedCode07/iOS-date-time-picker)
<br>
Add IOS like date time picker in your app. Get rid android date and time picker with single Widget to receive both date and time.
<img src="https://github.com/blessedCode07/IOS-Date-Time-Picker/blob/master/lib_gif.gif" width="40%" height="40%"> 

## Including in your project

#### build.gradle

```gradle

dependencies {

    implementation 'com.github.blessedCode07:iOS-date-time-picker:1.04'
}

```

### Add it in your root build.gradle at the end of repositories:

```gradle

allprojects {

  repositories {
    ...
    maven { url 'https://jitpack.io' }
    }
}

```

### Use IOS-Date-Time-Picker in your project

We can use `IOS-Date-Time-Picker` without any customized attributes.<br>


```gradle

val startDate: Calendar = Calendar.getInstance()

val dateTimeSelectedListener = object :

                OnDateTimeSelectedListener {
                override fun onDateTimeSelected(selectedDateTime: Calendar) {
                    //This is the calendar reference of selected date and time. 
                    //We can format the date time as we need here.
                    println("Selected date ${selectedDateTime.time}")

                }

            }



 val dateTimePickerDialog = DialogDateTimePicker(

                this, //context
                startDate, //start date of calendar
                12, //No. of future months to shown in calendar 
                dateTimeSelectedListener,
                "Select date and time") //Dialog title

                

//To show the date time picker dialog

dateTimePickerDialog.show()



```

### Dialog Customizations

```gradle



dateTimePickerDialog.setTitleTextColor(android.R.color.black)

dateTimePickerDialog.setDividerBgColor(android.R.color.black)


dateTimePickerDialog.setCancelBtnColor(R.color.colorAccent)
dateTimePickerDialog.setCancelBtnTextColor(R.color.colorPrimaryDark)


dateTimePickerDialog.setSubmitBtnColor(R.color.colorAccent)
dateTimePickerDialog.setSubmitBtnTextColor(R.color.colorPrimaryDark)


dateTimePickerDialog.setCancelBtnText("Dismiss")
dateTimePickerDialog.setSubmitBtnText("OK")


//Call dateTimePickerDialog.show() after all the customization is set on dialog.
//So dateTimePickerDialog.show() will be called last.

```

## Find this library useful? :heart:

Support it by :star: for this repository.
