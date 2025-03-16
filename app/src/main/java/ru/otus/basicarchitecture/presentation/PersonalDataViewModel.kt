package ru.otus.basicarchitecture.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import ru.otus.basicarchitecture.data.WizardCache
import java.util.Date

const val AGE_LIMIT = 568036800000

class PersonalDataViewModel @Inject constructor(private val wizardCache: WizardCache) :
    ViewModel() {
    val name = MutableLiveData<String>()
    val surname = MutableLiveData<String>()
    val date = MutableLiveData<Long>()
    val isDateValid = MutableLiveData<Boolean>()

    fun saveBirthDate(time: Long) {
        date.value = time
        val now = Date().time
        isDateValid.value = (now - time > AGE_LIMIT)
    }

    fun saveToCash() {
        wizardCache.name = name.value ?: ""
        wizardCache.surname = surname.value ?: ""
        wizardCache.birthDate = date.value ?: 0
    }
}