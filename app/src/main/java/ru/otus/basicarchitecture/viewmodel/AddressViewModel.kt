package ru.otus.basicarchitecture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.WizardCache
import ru.otus.basicarchitecture.model.AddressService
import ru.otus.basicarchitecture.model.AddressValue
import javax.inject.Inject

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
                println("Ошибочка")
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