package ru.otus.basicarchitecture.address

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.DataCache

class AddressFragmentModel : ViewModel(){
    private val _country = MutableLiveData("")
    val name: LiveData<String> = _country

    private val _city = MutableLiveData("")
    val city: LiveData<String> = _city

    private val _address = MutableLiveData("")
    val address: LiveData<String> = _address

    fun isButtonEnabled(): Boolean {
        return _country.value!!.isNotEmpty() && _city.value!!.isNotEmpty() && _address.value!!.isNotEmpty()
    }

    fun onNextButtonClicked() {
        DataCache.country = _country.value.toString()
        DataCache.city = _city.value.toString()
        DataCache.address = _address.value.toString()
    }

    fun checkTextFields(countryText: Editable?, cityText: Editable?, addressText: Editable?) {
        _country.value = countryText.toString()
        _city.value = cityText.toString()
        _address.value = addressText.toString()
    }
}