package ru.otus.basicarchitecture.ui.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.WizardCacheStorage
import ru.otus.basicarchitecture.retrofit.AddressService
import ru.otus.basicarchitecture.retrofit.LocationValue
import javax.inject.Inject

@HiltViewModel
class LocationFragmentViewModel @Inject constructor(
    private val wizardCacheStorage: WizardCacheStorage,
    private val daDataService: AddressService
) : ViewModel() {

    val locationResult = MutableLiveData<List<LocationValue>?>()
    private var retrofitJob: Job? = null
    fun search(query: String) {
        retrofitJob = viewModelScope.launch {
            try {
                daDataService.request(query) { response ->
                    locationResult.value = response?.suggestions
                }
            }
            catch (e: Exception) {
                println(e.message)
            }
        }
    }

    fun cancelRequest() {
        retrofitJob?.cancel()
        retrofitJob = null
    }

    fun saveDataToStorage(location: String) {
        wizardCacheStorage.cache.value = wizardCacheStorage.cache.value?.copy(
            location = location
        )
    }
}