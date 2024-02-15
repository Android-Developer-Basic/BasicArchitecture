package ru.otus.basicarchitecture.presentation.SecondScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.domain.Model.Address
import ru.otus.basicarchitecture.domain.setData.SetAddressUseCase
import javax.inject.Inject

class SecondScreenViewModel @Inject constructor (
    private val setAddressUseCase: SetAddressUseCase
) : ViewModel() {


    private val enabledButtonMutableLiveData = MutableLiveData<Boolean>()
    val enabledButtonLiveData = enabledButtonMutableLiveData

    private var country: String = UNKNOWN_VALUE
    private var city: String = UNKNOWN_VALUE
    private var address: String = UNKNOWN_VALUE

    //по хорошему подтягивать бы данные из визард кэша
    init {
        enabledButtonMutableLiveData.postValue(true)
    }

    fun setData(country: String?, city: String?, address: String?,  openFragment: () -> Unit){
        this.country = country ?: UNKNOWN_VALUE
        this.city = city ?: UNKNOWN_VALUE
        this.address = address ?: UNKNOWN_VALUE
        if(validateEmptyValue(this.country, this.city , this.address)) {
            val addres = Address(this.country, this.city, this.address)
            setAddressUseCase.setAddress(addres)
            openFragment.invoke()
        }
    }

    //убрать костыль с параметрами
    fun validateEmptyValue(country: String, city: String, address: String): Boolean {
        return if (country == UNKNOWN_VALUE || city == UNKNOWN_VALUE || address == UNKNOWN_VALUE) {
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




    companion object{
        private val UNKNOWN_VALUE = ""
    }
}