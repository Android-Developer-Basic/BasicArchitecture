package ru.otus.basicarchitecture.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.WizardCache
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.otus.basicarchitecture.model.AddressService
import ru.otus.basicarchitecture.model.AddressValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@HiltViewModel
class AddressViewModel @Inject constructor(): ViewModel() {
    @Inject lateinit var wizardCache: WizardCache
    private val resultAddressLive = MutableLiveData<List<AddressValue>?>()
    @Inject lateinit var service : AddressService
    private var activeJob: Job? = null
    fun saveData(address: String) {
        wizardCache.saveAddressData(address)
    }

    fun search(query: String) {
        activeJob = viewModelScope.launch {
            try {
                service.request(query) { response ->
                    resultAddressLive.value = response?.suggestions
                }
            }
            catch (e: Exception) {
                println("Ошибка Coroutine")
            }
        }
    }
    fun cancel() {
        activeJob?.cancel()
        activeJob = null
    }
    fun getResultAddressLive(): LiveData<List<AddressValue>?> {
        return resultAddressLive
}
}