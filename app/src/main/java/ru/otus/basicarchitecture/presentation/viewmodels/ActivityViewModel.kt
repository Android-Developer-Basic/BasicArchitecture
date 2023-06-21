package ru.otus.basicarchitecture.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.StateFlags

class ActivityViewModel(): ViewModel() {


    private val mutableLiveData = MutableLiveData<StateFlags>()
    val liveData:LiveData<StateFlags> get() = mutableLiveData

    init {
        mutableLiveData.value = StateFlags.FRAGMENT_1
    }

    fun setFlag(flag: StateFlags){
        mutableLiveData.value = flag
    }


}