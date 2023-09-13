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
    val validateState = MutableLiveData<ValidateState>()
    val viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState())

    fun validateAndSaveFirst() {
        validateState.value = isValidFirst(viewState.value!!.dateOfBirth,
            listOf(listOf(viewState.value!!.firstName, "Имя"), listOf(viewState.value!!.lastName, "Фамилия"))
        )
    }
    fun isValidFirst(dateOfBirth: String, fields: List<List<String>>): ValidateState {
        val stats = viewState.value!!
        val checkFiles = isValidFields(fields)
        if (checkFiles.isNotEmpty())
            return ValidateState.LoseFiled(checkFiles)

        if (dateOfBirth.isEmpty()) {
            return ValidateState.LoseFiled("Дата рождения")
        }
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.US)
        try {
            val date = format.parse(dateOfBirth)
//            val calendar = Calendar.getInstance()
//            calendar.time = date
//            val year = calendar.get(Calendar.YEAR)
//            val month = calendar.get(Calendar.MONTH) + 1
//            val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
//
//
//            if (!(day in 1..31 && month in 1..12 && year in 1900..currentYear)) {
//                return ValidateState.BedFiled("Дата рождения")
//            }

//            val cal = Calendar.getInstance()
//            cal.time = date
//            cal.add(Calendar.YEAR, 18)
//            return if (cal.time.before(Date())){
            return if (2023 - dateOfBirth.split(".")[2].toInt() > 18){
                wizardCache.firstName = stats.firstName
                wizardCache.lastName = stats.lastName
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

}