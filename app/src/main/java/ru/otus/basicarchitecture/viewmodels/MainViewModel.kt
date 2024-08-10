package ru.otus.basicarchitecture.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _state = MutableLiveData<MainViewState>()
    val viewState: LiveData<MainViewState> = _state
//    private val _state = MutableLiveData<MainViewState>()
//    val viewState: LiveData<MainViewState> = _state

}