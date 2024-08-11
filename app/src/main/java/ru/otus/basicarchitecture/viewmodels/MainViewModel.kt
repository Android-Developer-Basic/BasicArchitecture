package ru.otus.basicarchitecture.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.model.InputData
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {
    private val _inputData = MutableLiveData<InputData>()
    val inputData: LiveData<InputData> get() = _inputData

    fun setInputData(data: InputData) {
        _inputData.value = data
    }
}