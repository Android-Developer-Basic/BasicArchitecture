package ru.otus.basicarchitecture.presentation.viewmodels

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.domain.models.User
import ru.otus.basicarchitecture.domain.models.UserBirthDate
import ru.otus.basicarchitecture.domain.models.UserName
import ru.otus.basicarchitecture.domain.models.UserSurname
import ru.otus.basicarchitecture.domain.models.ViewModelData
import ru.otus.basicarchitecture.domain.usecases.data_use_cases.GetDataUseCase
import ru.otus.basicarchitecture.domain.usecases.date_use_cases.PickDateUseCase
import ru.otus.basicarchitecture.domain.usecases.data_use_cases.SaveDataUseCase
import ru.otus.basicarchitecture.domain.usecases.date_use_cases.ValidationUseCase


class Fragment1ViewModel(
    private val getDataUseCase: GetDataUseCase,
    private val saveDataUseCase: SaveDataUseCase,
    private val pickDateUseCase: PickDateUseCase,
    private val validationUseCase: ValidationUseCase,
):ViewModel() {

    private val mutableOutputData = MutableLiveData<User>()
    val outputData:LiveData<User>get() = mutableOutputData
    val inputData = MutableLiveData<List<ViewModelData>>()

    init {
        loadData()

    }


    private fun loadData(){
        mutableOutputData.value = getDataUseCase.getUser()
    }

    fun pickDate(context: Context, textView: TextView){
        pickDateUseCase.pickDate(context, textView)
    }


    fun validation():Boolean{
        return validationUseCase.validation()
    }



    fun saveData(){
        inputData.value.apply {
            val userList = this
            saveDataUseCase.apply {
                setUserName(userList?.get(0) as UserName)
                setUserSurname(userList[1] as UserSurname)
                setUserBirthDate(userList[2] as UserBirthDate)
            }
        }
        loadData()
    }



}