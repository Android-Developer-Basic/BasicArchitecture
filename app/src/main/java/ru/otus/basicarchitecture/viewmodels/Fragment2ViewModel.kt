package ru.otus.basicarchitecture.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.repository.WizardCache
import javax.inject.Inject
@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class Fragment2ViewModel @Inject constructor(private val wizardCache: WizardCache) : ViewModel() {
    val country = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val address = MutableLiveData<String>()

    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean> get() = _isFormValid

    init {
        country.observeForever { validateForm() }
        city.observeForever { validateForm() }
        address.observeForever { validateForm() }
    }

    private fun validateForm() {
        val isCountryValid = !country.value.isNullOrBlank()
        val isCityValid = !city.value.isNullOrBlank()
        val isAddressValid = !address.value.isNullOrBlank()
        _isFormValid.value = isCountryValid && isCityValid && isAddressValid
    }

    fun saveData() {
        wizardCache.country = country.value
        wizardCache.city = city.value
        wizardCache.address = address.value
    }
}