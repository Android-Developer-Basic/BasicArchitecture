package ru.otus.basicarchitecture.address

import androidx.lifecycle.MutableLiveData
import ru.otus.basicarchitecture.DataCache
import javax.inject.Inject

class AddressFragmentViewModel @Inject constructor(
    private val dataCache: DataCache
){
    val city = MutableLiveData<String>()
    val country = MutableLiveData<String>()
    val address = MutableLiveData<String>()

    fun save () {
        dataCache.city = city.value ?: ""
        dataCache.country= country.value ?: ""
        dataCache.address = address.value ?: ""
    }
}


