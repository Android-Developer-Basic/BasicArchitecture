package ru.otus.basicarchitecture.domain.repository

import android.content.Context
import android.widget.TextView

interface DatePicker {

    fun showDatePickerDialog(context: Context, textView: TextView)

    fun validation():Boolean
}