package ru.otus.basicarchitecture.name

import androidx.lifecycle.MutableLiveData
import ru.otus.basicarchitecture.DataCache
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class NameFragmentViewModel @Inject constructor(
    private val dataCache: DataCache
) {
    val name = MutableLiveData<String>()
    val surname = MutableLiveData<String>()
    val date = MutableLiveData<String>()

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val error = MutableLiveData<String?>()

    fun validateDateOfBirth(date: String): Boolean {
        try {
            val birthDate = dateFormat.parse(date)!!
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -18)
            if (birthDate.after(calendar.time)) {
                error.value = "You are under 18 years old"
                return false
            }
        } catch (e: Throwable) {
            error.value = "Incorrect date"
            return false
        }
        return true
    }

    fun clearValidation() {
        error.value = null
    }

    fun save() {
        dataCache.name = name.value ?: ""
        dataCache.surname = surname.value ?: ""
        dataCache.dateOfBirth = date.value!!
    }
}