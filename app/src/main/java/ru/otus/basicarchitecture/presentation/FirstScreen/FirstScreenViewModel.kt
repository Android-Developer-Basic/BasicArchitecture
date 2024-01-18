package ru.otus.basicarchitecture.presentation.FirstScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.data.RepositoryImpl
import ru.otus.basicarchitecture.domain.Model.Person
import ru.otus.basicarchitecture.domain.Repository
import ru.otus.basicarchitecture.domain.setData.SetPersonUseCase

class FirstScreenViewModel : ViewModel(){


//    private val personMutableLiveData = MutableLiveData<Person>()
//
//
//    val personLiveData = personMutableLiveData
//
    var repository = RepositoryImpl()
    private val setPersonUseCase = SetPersonUseCase(repository)


    private val enabledButtonMutableLiveData = MutableLiveData<Boolean>()
    val enabledButtonLiveData = enabledButtonMutableLiveData

    private var name = UNKNOWN_VALUE
    private var surName = UNKNOWN_VALUE
    private var birthDate  = UNKNOWN_VALUE

    fun validate(){

    }


    companion object{
        private val UNKNOWN_VALUE = ""
    }


}