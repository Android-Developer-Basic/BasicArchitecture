package ru.otus.basicarchitecture.Ui.Fragment1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.Core.Model.Person
import ru.otus.basicarchitecture.Domain.Data.PersonUseCase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class Fragment1ViewModel @Inject constructor(
    val personUseCase: PersonUseCase
) : ViewModel() {
    private val enabledButtonMutableLiveData = MutableLiveData<Boolean>()
    val enabledButtonLiveData = enabledButtonMutableLiveData

    private var name = DEFFAULT_VALUE
    private var surName = DEFFAULT_VALUE
    private var birthDate = DEFFAULT_VALUE


    init {
        enabledButtonMutableLiveData.postValue(false)
    }

    fun setData(name: String?, surName: String?, birthDate: String?, openFragment: () -> Unit) {
        this.name = name ?: DEFFAULT_VALUE
        this.surName = surName ?: DEFFAULT_VALUE
        this.birthDate = birthDate ?: DEFFAULT_VALUE
        if(validateEmptyValue()){
            val person = Person(this.name, this.surName, this.birthDate)
            personUseCase.setPerson(person)
            openFragment.invoke()
        }
    }

    fun validateEmptyValue(): Boolean {
        return if (name.isEmpty() || surName.isEmpty() || birthDate.isEmpty()) {
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
        private val DEFFAULT_VALUE = ""
    }
}