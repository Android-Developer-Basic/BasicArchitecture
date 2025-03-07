package ru.otus.basicarchitecture.ui.authorization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.ui.data.WizardCache
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private var wizardCache: WizardCache
) : ViewModel() {

    val anAdult = MutableLiveData<Boolean>()

    private var currentDay = 0
    private var currentMonth = 0
    private var currentYear = 0

    fun setName(name: String) {
        wizardCache.personName = name
    }

    fun setSurname(surname: String) {
        wizardCache.personSurname = surname
    }

    private fun setBirthDate(day: Int, month: Int, year: Int) {
        if (anAdult.value == true) {
            val date = (day.toString() + "-" + (month + 1) + "-" + year)
            wizardCache.personBirthDate = date
        }
    }

    fun ageVerification(day: Int, month: Int, year: Int) {

        val yerDifferent = currentYear - year
        val monthMore = currentMonth >= month
        val dayMore = currentDay >= day

        if (yerDifferent > 18) {
            anAdult.value = true
        } else {
            anAdult.value = yerDifferent >= 18 && monthMore && dayMore
        }

        setBirthDate(day, month, year)
    }

    fun setCurrentDate(day: Int, month: Int, year: Int) {
        currentDay = day
        currentMonth = month
        currentYear = year
    }
}