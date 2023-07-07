package ru.otus.basicarchitecture

import android.icu.util.Calendar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class UserInputViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {
    val _state = MutableLiveData<ValidateState>()
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val dateOfBirth = MutableLiveData<String>()
    val country = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    val selectedTags = mutableListOf<String>()

    fun validateAndSaveFirst() {
        _state.value = isValidFirst(dateOfBirth.value?:"",
            listOf(listOf(firstName.value?:"", "Имя"), listOf(lastName.value?:"", "Фамилия"))
        )
    }

    fun getWizardCache(): WizardCache {
        return wizardCache
    }

    fun validateAndSaveAddress() {
        val checkFiles = isValidFields(
            listOf(
                listOf(country.value?:"", "Строна"),
                listOf(city.value?:"", "Город"),
                listOf(address.value?:"", "Адрес")
            ))

        _state.value = if (checkFiles.isNotEmpty())
            ValidateState.LoseFiled(checkFiles)
        else {
            wizardCache.country = country.value
            wizardCache.city = city.value
            wizardCache.address = address.value
            ValidateState.Ok
        }

    }


    private fun isValidFirst(dateOfBirth: String, fields: List<List<String>>): ValidateState {
        val checkFiles = isValidFields(fields)
        if (checkFiles.isNotEmpty())
            return ValidateState.LoseFiled(checkFiles)

        if (dateOfBirth.isEmpty()) {
            return ValidateState.LoseFiled("Дата рождения")
        }
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.US)
        try {
            val date = format.parse(dateOfBirth)
            val calendar = Calendar.getInstance()
            calendar.time = date
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val currentYear = Calendar.getInstance().get(Calendar.YEAR)


            if (!(day in 1..31 && month in 1..12 && year in 1900..currentYear)) {
                return ValidateState.BedFiled("Дата рождения")
            }

            val cal = Calendar.getInstance()
            cal.time = date
            cal.add(Calendar.YEAR, 18)
            return if (cal.time.before(Date())){
                wizardCache.firstName = firstName.value
                wizardCache.lastName = lastName.value
                wizardCache.dateOfBirth = date
                ValidateState.Ok
                }
            else
                ValidateState.Not18

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ValidateState.BedFiled("Дата рождения")


    }

    private fun isValidFields(fields: List<List<String>>): String {
        for (field in fields)
            if (field[0].isEmpty()) {
                return field[1]
            }
        return ""
    }

    fun saveTags() {
        wizardCache.selectedTags = selectedTags
    }
}