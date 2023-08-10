package ru.otus.basicarchitecture.name

import android.os.Build
import android.text.Editable
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.DataCacheStorage
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class NameFragmentModel @Inject constructor(private val dataCacheStorage: DataCacheStorage) : ViewModel() {

    private val _state = MutableLiveData<NameFragmentState>()
    val nameFragmentState: LiveData<NameFragmentState> = _state

    init {
        _state.postValue(
            NameFragmentState(
                dataCacheStorage.cache.value?.name ?: "",
                dataCacheStorage.cache.value?.surname ?: "",
                dataCacheStorage.cache.value?.birthday ?: Date(),
                isAdult = checkIsAdult(dataCacheStorage.cache.value?.birthday ?: Date()),
                accessNextButton = checkedAssessButton(),
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkIsAdult(birthday: Date): Boolean {
        val birthdayDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val todayDate = LocalDate.now()
        return Period.between(birthdayDate,todayDate).years >= 18
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSetAge(birthday: Date) {
        val currentState = _state.value ?: NameFragmentState()
        _state.postValue(
            currentState.copy(
                date = birthday,
                isAdult = checkIsAdult(birthday),
                accessNextButton = checkedAssessButton(),
            )
        )
    }

    fun onNextButtonClicked() {
        dataCacheStorage.cache.value = dataCacheStorage.cache.value?.copy(
            name = _state.value?.name.toString(),
            surname = _state.value?.surname.toString(),
            birthday = _state.value?.date ?: Date()
        )
    }

    fun checkTextFields(nameText: Editable?, surnameText: Editable?) {
        val currentState = _state.value ?: NameFragmentState()
        _state.postValue(
            currentState.copy(
                name = nameText.toString(),
                surname = surnameText.toString(),
                accessNextButton = checkedAssessButton(),
            )
        )
    }
    private fun checkedAssessButton(): Boolean {
        return /*_state.value?.name?.isNotEmpty() == true && _state.value?.surname?.isNotEmpty() == true &&*/ _state.value?.isAdult == true
    }
    fun isButtonEnabled(): Boolean {
        return _state.value?.accessNextButton ?: false
    }
}