package ru.otus.basicarchitecture.viewmodel

import android.os.Build
import android.text.Editable
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.WizardCache
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(): ViewModel() {
    @Inject lateinit var wizardCache: WizardCache

    private val resultValidDateLive = MutableLiveData<Boolean?>()

    private var date:LocalDate? = null

    var ignore = false
    var sb = StringBuilder()
    private val numPlace = 'X'

    fun onNext(name:String, surname:String) {
        wizardCache.saveNameData(name, surname, date!!)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onSaveDate(date: LocalDate?) {
        this.date = date
        if(date == null) {
            resultValidDateLive.value = null
            return
        }
        resultValidDateLive.value = Period.between(date, LocalDate.now()).years >= 18
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onDateChanged(editable: Editable?) {
        if (!ignore) {
            removeFormat(editable.toString());
            applyFormat(sb.toString());
            ignore = true;
            editable?.replace(0, editable.toString().length, sb.toString());
            if(editable.toString().length == getTemplate().length) {
                toDate(editable.toString())
                onSaveDate(toDate(editable.toString()))
            } else {
                onSaveDate(null)
            }
            ignore = false;
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun toDate(dateStr:String): LocalDate? {
        var date: LocalDate? = null
        try {
            date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        } catch (e : DateTimeParseException) {

        }
        return date
    }

    private fun removeFormat(text: String) {
        sb.setLength(0)
        for (element in text) {
            if (isNumberChar(element)) {
                sb.append(element)
            }
        }
    }
    private fun applyFormat(text: String) {
        val template: String = getTemplate()
        sb.setLength(0)
        var i = 0
        var textIndex = 0
        while (i < template.length && textIndex < text.length) {
            if (template[i] == numPlace) {
                sb.append(text[textIndex])
                textIndex++
            } else {
                sb.append(template[i])
            }
            i++
        }
    }

    private fun isNumberChar(c: Char): Boolean {
        return c in '0'..'9'
    }

    private fun getTemplate(): String {
        return "XX.XX.XXXX";
    }

    fun getResultValidDateLive(): LiveData<Boolean?> {
        return resultValidDateLive
    }
}