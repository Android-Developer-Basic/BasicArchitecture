package ru.otus.basicarchitecture.Ui.Fragment1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.Core.Model.Address
import ru.otus.basicarchitecture.Domain.Data.AddressUseCase
import javax.inject.Inject

class Fragment2ViewModel  @Inject constructor (
    private val addressUseCase: AddressUseCase
) : ViewModel() {


    private val enabledButtonMutableLiveData = MutableLiveData<Boolean>()
    val enabledButtonLiveData = enabledButtonMutableLiveData
    private val UNKNOWN_VALUE = ""
    private var country: String = UNKNOWN_VALUE
    private var city: String = UNKNOWN_VALUE
    private var address: String = UNKNOWN_VALUE

    init {
        enabledButtonMutableLiveData.postValue(true)
    }

    fun setData(country: String?, city: String?, address: String?,  openFragment: () -> Unit){
        this.country = country ?: UNKNOWN_VALUE
        this.city = city ?: UNKNOWN_VALUE
        this.address = address ?: UNKNOWN_VALUE
        if(validateEmptyValue(this.country, this.city , this.address)) {
            val addres = Address(this.country, this.city, this.address)
            addressUseCase.setAddress(addres)
            openFragment.invoke()
        }
    }

    fun validateEmptyValue(country: String, city: String, address: String): Boolean {
        return if (country.isEmpty() || city.isEmpty() || address.isEmpty()) {
            enabledButtonMutableLiveData.postValue(false)
            false
        } else {
            enabledButtonMutableLiveData.postValue(true)
            true
        }
    }

    fun buttonEnabled(){
        enabledButtonMutableLiveData.postValue(true)
    }
}