package ru.otus.basicarchitecture.viewmodel

import android.R
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.WizardCache
import ru.otus.basicarchitecture.model.AddressService
import ru.otus.basicarchitecture.model.AddressValue
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(): ViewModel() {
    @Inject lateinit var wizardCache: WizardCache
    private val resultAddressLive = MutableLiveData<List<AddressValue>?>()
    private val service = AddressService()
    fun saveData(address: String) {
        wizardCache.saveAddressData(address)
    }

    fun search(query: String) {
        service.request(query) { response ->
            resultAddressLive.value = response?.suggestions
            }
    }

    fun getResultAddressLive(): LiveData<List<AddressValue>?> {
        return resultAddressLive
    }
}