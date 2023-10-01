package ru.otus.basicarchitecture.ui.result

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.WizardCache
import ru.otus.basicarchitecture.WizardCacheStorage
import ru.otus.basicarchitecture.utils.DateTimeConverter
import ru.otus.basicarchitecture.utils.DateTimeConverter.formatDate
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ResultFragmentViewModel @Inject constructor(private val wizardCacheStorage: WizardCacheStorage) : ViewModel() {
    private val _resultState = MutableLiveData<WizardCache>()
    val resultState: LiveData<WizardCache> = _resultState

    fun requestWizardCache(){
        _resultState.postValue(
            wizardCacheStorage.cache.value
        )
    }
}