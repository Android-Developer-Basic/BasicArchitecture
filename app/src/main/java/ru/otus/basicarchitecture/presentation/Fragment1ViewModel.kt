package ru.otus.basicarchitecture.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Fragment1ViewModel : ViewModel() {
    private val _state = MutableLiveData<MainViewState>()
    val viewState: LiveData<MainViewState> = _state
}