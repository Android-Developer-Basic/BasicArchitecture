package ru.otus.basicarchitecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserInputTagsViewModel @Inject constructor(
    val wizardCache: WizardCache
) : ViewModel() {
    val viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState())

    fun saveTags() {
        wizardCache.selectedTags = viewState.value!!.selectedTags
    }
}