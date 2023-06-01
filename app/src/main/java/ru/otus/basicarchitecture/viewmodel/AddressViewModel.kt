package ru.otus.basicarchitecture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.WizardCache
import ru.otus.basicarchitecture.model.Repository
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(): ViewModel() {
    @Inject lateinit var wizardCache: WizardCache
    fun saveData(country: String, city:String, address: String) {
        wizardCache.saveAddressData(country,city,address)
    }
}