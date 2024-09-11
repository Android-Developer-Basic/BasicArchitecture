package ru.otus.basicarchitecture.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import ru.otus.basicarchitecture.data.WizardCache
import javax.inject.Inject

class AddressViewModel @Inject constructor(private val wizardCache: WizardCache) {

    val city = MutableLiveData<String>()
    val country = MutableLiveData<String>()
    val address = MutableLiveData<String>()

    fun save() {
        wizardCache.country = country.value ?: ""
        wizardCache.city = city.value ?: ""
        wizardCache.address = address.value ?: ""
    }
}