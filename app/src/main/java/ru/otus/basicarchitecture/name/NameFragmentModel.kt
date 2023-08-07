package ru.otus.basicarchitecture.name

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.text.Editable
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.DataCache
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.Date
import java.util.Locale

class NameFragmentModel : ViewModel() {

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    private val _surname = MutableLiveData("")
    val surname: LiveData<String> = _surname

    private val _date = MutableLiveData("")
    val date: LiveData<String> = _date

    private val _isAdult = MutableLiveData(false)
    val accessNextButton: LiveData<Boolean> = _isAdult

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSetAge(birthday: Date) {
        val birthdayDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val todayDate = LocalDate.now()
        val yearDiff = Period.between(birthdayDate,todayDate).years
        _date.value = SimpleDateFormat("dd.MM.yyyy", Locale.UK).format(birthday.time)
        _isAdult.value = yearDiff >= 18
    }

    fun onNextButtonClicked() {
        DataCache.name = _name.value.toString()
        DataCache.surname = _surname.value.toString()
        DataCache.birthday = _date.value.toString()
    }

    fun checkTextFields(nameText: Editable?, surnameText: Editable?) {
        _name.value = nameText.toString()
        _surname.value = surnameText.toString()
    }

    fun isButtonEnabled(): Boolean {
        return _name.value!!.isNotEmpty() && _surname.value!!.isNotEmpty() && _isAdult.value == true
    }
}