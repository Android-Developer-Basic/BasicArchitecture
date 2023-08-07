package ru.otus.basicarchitecture.presentation.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.domain.models.User
import ru.otus.basicarchitecture.domain.models.UserAddress
import ru.otus.basicarchitecture.domain.models.ViewModelData
import ru.otus.basicarchitecture.domain.usecases.data_use_cases.GetDataUseCase
import ru.otus.basicarchitecture.domain.usecases.data_use_cases.SaveDataUseCase

class Fragment2ViewModel(
    private val getDataUseCase: GetDataUseCase,
    private val saveDataUseCase: SaveDataUseCase,
): ViewModel(

) {
    private val mutableOutputData = MutableLiveData<User>()
    val outputData: LiveData<User>
        get() = mutableOutputData
    val inputData = MutableLiveData<ViewModelData>()

    init {
        loadData()

    }

    private fun loadData(){
        mutableOutputData.value = getDataUseCase.getUser()
    }


    fun saveData() {
        inputData.value.apply {
            val userAddress = this as UserAddress
            saveDataUseCase.setUserAddress(userAddress)
        }
        loadData()
    }
}