package ru.otus.basicarchitecture.address

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.WizardCache
import ru.otus.basicarchitecture.databinding.AddressFragmentBinding
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val useCase: AddressUseCase,
    private val cache: WizardCache
) : ViewModel() {

    fun fillFieldsFromCache(binding: AddressFragmentBinding) {
        binding.countryField.setText(cache.country)
        binding.cityField.setText(cache.city)
        binding.addressField.setText(cache.address)
    }

    fun saveFieldsToCache(binding: AddressFragmentBinding) {
        cache.country = binding.countryField.text?.toString() ?: ""
        cache.city = binding.cityField.text?.toString() ?: ""
        cache.address = binding.addressField.text?.toString() ?: ""
    }

}