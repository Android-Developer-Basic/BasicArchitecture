package ru.otus.basicarchitecture.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object DateTimeConverter {
    @SuppressLint("SimpleDateFormat")
    val formatTime = SimpleDateFormat("HH:mm:ss")

    @SuppressLint("SimpleDateFormat")
    val formatDate = SimpleDateFormat("dd.MM.yyyy")

    @SuppressLint("SimpleDateFormat")
    val formatDateTime = SimpleDateFormat("dd.MM.yyyy HH:mm")

    fun Long.toTime(): String {
        val currentTime = Date(this)
        return formatTime.format(currentTime)
    }

    fun Long.toDate(): String {
        val currentDate = Date(this)
        return formatDate.format(currentDate)
    }

    fun Long.toDateTime(): String {
        val currentDate = Date(this)
        return formatDateTime.format(currentDate)
    }

    fun currentTimeMillis() = System.currentTimeMillis()

    fun currentDateTimeMillis() = Calendar.getInstance().timeInMillis
}