package ru.otus.basicarchitecture.presentation.addressFragment

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.repository.WizardCache
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {

    var country: String? = null
    var city: String? = null
    var address: String? = null

    fun saveData(): Boolean {
        wizardCache.country = country
        wizardCache.city = city
        wizardCache.address = address
        return true
    }
}