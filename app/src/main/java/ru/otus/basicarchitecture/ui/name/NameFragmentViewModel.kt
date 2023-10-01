package ru.otus.basicarchitecture.ui.name


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.WizardCacheStorage
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class NameFragmentViewModel @Inject constructor(private val wizardCacheStorage: WizardCacheStorage) : ViewModel() {
    private val _ageState = MutableLiveData(Date())
    val ageState: LiveData<Date> = _ageState

    init {
        _ageState.postValue(
            Date()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkIsAdult(birthday: Date): Boolean {
        val birthdayDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val todayDate = LocalDate.now()
        return Period.between(birthdayDate,todayDate).years >= 18
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSetAge(birthday: Date) {
        _ageState.postValue(
            birthday
        )
    }

    fun saveDataToStorage(model: NameModel) {
        wizardCacheStorage.cache.value = wizardCacheStorage.cache.value?.copy(
            name = model.name,
            surname = model.surname,
            birth = model.birth
        )
    }
}