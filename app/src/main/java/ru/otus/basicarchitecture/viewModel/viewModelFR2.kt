package ru.otus.basicarchitecture.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.otus.WizardCache
import ru.otus.basicarchitecture.internetAdrdess.Address
import ru.otus.basicarchitecture.internetAdrdess.AddressAnswer
import ru.otus.basicarchitecture.internetAdrdess.AddressServiceModule
import ru.otus.basicarchitecture.internetAdrdess.Query
import ru.otus.basicarchitecture.viewState.viewStateFR2
import javax.inject.Inject


@HiltViewModel
open class viewModelFR2 @Inject constructor(private val cache: WizardCache): ViewModel() {

    val _stateFR2: MutableLiveData<viewStateFR2> =
        MutableLiveData<viewStateFR2>(  //хранит состояние (viewModel)
            viewStateFR2() // Начальное значение
        ) //для обращений внурти (тут)

    val viewState: LiveData<viewStateFR2> get() = _stateFR2.distinctUntilChanged()  //в фрагментах/активити использовать это поле для обращения к состоянию
    //для обращений снаружи(ativity/frahment)

    private fun renderView(variantAddress: List<String> = emptyList()) {   //обновлялка viewState (синхронизация с WizardCache)
        _stateFR2.value = with(cache) {
            viewStateFR2(
                address = address,
                variantAddress = variantAddress
            )
        }
    }


    fun setAddress(value: String) {

        if (value == cache.address) return

        cache.address = value
        renderView()

        viewModelScope.launch() {
            val goToInet = AddressServiceModule().retrofitService()  //создание ретрофита
            //передача в сеть введенного сдлова и получение (если есть чего)
            val response = goToInet.getAddressAnswer(Query(value))
            if (response.isSuccessful) {
                val answer: AddressAnswer? = response.body()
                val suggestions: List<Address> = answer?.suggestions.orEmpty()
                val addressList: List<String> = suggestions.map { address -> address.value }
                renderView(addressList)
            }
        }
    }
}