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
        enabledButtonMutableLiveData.postValue(false)
    }

    fun setData(name: String?, surName: String?, birthDate: String?, openFragment: () -> Unit) {
        this.name = name ?: UNKNOWN_VALUE
        this.surName = surName ?: UNKNOWN_VALUE
        this.birthDate = birthDate ?: UNKNOWN_VALUE
        if(validateEmptyValue()){
            val person = Person(this.name, this.surName, this.birthDate)
            setPersonUseCase.setPerson(person)
            openFragment.invoke()
        }
    }

    fun validateEmptyValue(): Boolean {
        return if (name == UNKNOWN_VALUE || surName == UNKNOWN_VALUE || birthDate == UNKNOWN_VALUE) {
            enabledButtonMutableLiveData.postValue(false)
            false
        } else {
            enabledButtonMutableLiveData.postValue(true)
            true
        }
    }

    fun validData(day: Int, month: Int, year: Int, showToast: () -> Unit) {
        val correctAge = 18
        val currentDate = LocalDate.now()
        val monthText = if (month < 9) "0${month + 1}" else "${month + 1}"
        val dayText = if (day<10) "0${day+1}" else "$day"
        val selectedDateValue = LocalDate.parse("$year-$monthText-$dayText", DateTimeFormatter.ISO_DATE)
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

    fun enabledButton(){
        enabledButtonMutableLiveData.postValue(true)
    }


    companion object {
        private val UNKNOWN_VALUE = ""
    }


}