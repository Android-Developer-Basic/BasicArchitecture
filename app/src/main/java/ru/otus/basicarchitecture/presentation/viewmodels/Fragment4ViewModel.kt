package ru.otus.basicarchitecture.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.domain.models.User
import ru.otus.basicarchitecture.domain.usecases.data_use_cases.GetDataUseCase

class Fragment4ViewModel(
    private val getDataUseCase: GetDataUseCase
):ViewModel() {
    private val mutableData = MutableLiveData<User>()
    val data:LiveData<User> get() = mutableData

    init {
        mutableData.value = getDataUseCase.getUser()

    }

}