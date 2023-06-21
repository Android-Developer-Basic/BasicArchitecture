package ru.otus.basicarchitecture.data

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import ru.otus.basicarchitecture.domain.repository.DatePicker
import java.time.LocalDate
import java.util.Calendar

object DatePickerImpl: DatePicker {
    private val calendar = Calendar.getInstance()
    private val d = calendar.get(Calendar.DAY_OF_MONTH)
    private val m = calendar.get(Calendar.MONTH)
    private val y = calendar.get(Calendar.YEAR)
    private var date = "11/11/1991"
    private var day = 0
    private var month = 0
    private var year = 0
    lateinit var dpd: DatePickerDialog


    override fun showDatePickerDialog(context: Context, textView: TextView){

        dpd = DatePickerDialog(context,DatePickerDialog.OnDateSetListener{_, year, month, day ->
            this.year = year
            date = "$day/${month+1}/$year"
            textView.text = date
        }, y,m,d)
        dpd.show()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun validation(): Boolean {
        val currentDate = LocalDate.now()
        return currentDate.year - year >= 18
    }

}