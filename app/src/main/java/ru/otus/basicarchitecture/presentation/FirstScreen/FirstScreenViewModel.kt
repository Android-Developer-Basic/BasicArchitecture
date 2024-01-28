package ru.otus.basicarchitecture.presentation.FirstScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.domain.Model.Person
import ru.otus.basicarchitecture.domain.setData.SetPersonUseCase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class FirstScreenViewModel @Inject constructor(
    val setPersonUseCase: SetPersonUseCase
) : ViewModel() {

    private val enabledButtonMutableLiveData = MutableLiveData<Boolean>()
    val enabledButtonLiveData = enabledButtonMutableLiveData

    private var name = UNKNOWN_VALUE
    private var surName = UNKNOWN_VALUE
    private var birthDate = UNKNOWN_VALUE


    init {
        enabledButtonMutableLiveData.postValue(true)
    }

    fun setData(name: String?, surName: String?, birthDate: String?, showToast: () -> Unit, openFragment: () -> Unit) {
        this.name = name ?: UNKNOWN_VALUE
        this.surName = surName ?: UNKNOWN_VALUE
        this.birthDate = birthDate ?: UNKNOWN_VALUE
        if(validateEmptyValue(showToast)){
            val person = Person(this.name, this.surName, this.birthDate)
            setPersonUseCase.setPerson(person)
            openFragment.invoke()
        }
    }

    fun validateEmptyValue(showToast: () -> Unit): Boolean {
        return if (name == UNKNOWN_VALUE || surName == UNKNOWN_VALUE || birthDate == UNKNOWN_VALUE) {
            setupFalseButton(showToast)
            false
        } else {
            enabledButtonMutableLiveData.postValue(true)
            true
        }
    }

    fun validData(day: Int, month: Int, year: Int, showToast: () -> Unit) {
        val correctAge = 18
        val currentDate = LocalDate.now()
        val monthText = if (month < 10) "0${month + 1}" else "1${month + 1}"
        val selectedDateValue = LocalDate.parse("$year-$monthText-$day", DateTimeFormatter.ISO_DATE)
        val diff = ChronoUnit.YEARS.between(selectedDateValue, currentDate)
        if (diff < correctAge) {
            setupFalseButton(showToast)
        } else {
            enabledButtonLiveData.postValue(true)
        }
    }

    private fun setupFalseButton(showToast: () -> Unit) {
        enabledButtonMutableLiveData.postValue(false)
        showToast.invoke()
    }


    companion object {
        private val UNKNOWN_VALUE = ""
    }


}