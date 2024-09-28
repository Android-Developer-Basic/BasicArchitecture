package ru.otus.basicarchitecture.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.data.WizardCache
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class PersonViewModel @Inject constructor(private val wizardCache: WizardCache) : ViewModel() {

    val name = MutableLiveData<String>()
    val surname = MutableLiveData<String>()
    val date = MutableLiveData<String>()

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val error = MutableLiveData<String?>()

    fun validateDateOfBirth(date: String): Boolean {
        try {
            val birthDate = dateFormat.parse(date) ?: return false
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -18)
            if (birthDate.after(calendar.time)) {
                error.value = "You must be 18 or older"
                return false
            }
        } catch (e: Throwable) {
            error.value = "Invalid date format. Please enter a date in the format dd.MM.yyyy."
            return false
        }
        return true
    }

    fun clearValidation() {
        error.value = null
    }

    fun save() {
        wizardCache.name = name.value ?: ""
        wizardCache.surname = surname.value ?: ""
        wizardCache.dateOfBirth = date.value!!
    }
}