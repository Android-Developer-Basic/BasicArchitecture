package ru.otus.basicarchitecture.ui.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.WizardCacheStorage
import javax.inject.Inject

@HiltViewModel
class LocationFragmentViewModel @Inject constructor(private val wizardCacheStorage: WizardCacheStorage) : ViewModel() {
    private val _locationState = MutableLiveData<LocationModel>()
    val locationState: LiveData<LocationModel> = _locationState

    fun saveDataToStorage(model: LocationModel) {
        wizardCacheStorage.cache.value = wizardCacheStorage.cache.value?.copy(
            country = model.country,
            city = model.city,
            address = model.address
        )
    }
}