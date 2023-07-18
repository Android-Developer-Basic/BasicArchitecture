package ru.otus.basicarchitecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserInputAddressViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {
    val validateState = MutableLiveData<ValidateState>()
    val viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState())

    fun validateAndSaveAddress() {
        val stats = viewState.value!!
        val checkFiles = isValidFields(
            listOf(
                listOf(stats.country, "Строна"),
                listOf(stats.city, "Город"),
                listOf(stats.address, "Адрес")
            ))

        validateState.value = if (checkFiles.isNotEmpty())
            ValidateState.LoseFiled(checkFiles)
        else {
            wizardCache.country = stats.country
            wizardCache.city = stats.city
            wizardCache.address = stats.address
            ValidateState.Ok
        }
    }


    private fun isValidFields(fields: List<List<String>>): String {
        for (field in fields)
            if (field[0].isEmpty()) {
                return field[1]
            }
        return ""
    }
}