package ru.otus.basicarchitecture.Ui.Fragment1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.otus.basicarchitecture.Core.Model.Address
import ru.otus.basicarchitecture.Domain.Data.AddressUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.otus.basicarchitecture.Core.Model.DTO.Suggestion
import ru.otus.basicarchitecture.Core.Model.DTO.SuggestionRequest
import ru.otus.basicarchitecture.Core.Utils.ErrorService
import ru.otus.basicarchitecture.Core.Utils.ProgresService
import ru.otus.basicarchitecture.data.AddressImpl
import javax.inject.Inject

class Fragment2ViewModel  @Inject constructor (
    private val addressUseCase: AddressUseCase,
    private val repository: AddressImpl,
    public val progresService: ProgresService,
    public val errorService: ErrorService
) : ViewModel() {

    private var prevQuery = ""
    private val enabledButtonMutableLiveData = MutableLiveData<Boolean>()
    val enabledButtonLiveData = enabledButtonMutableLiveData

    private val mSuggestionsMutableLiveData = MutableLiveData<List<Suggestion>>()
    val suggestionsLiveData = mSuggestionsMutableLiveData


    private val UNKNOWN_VALUE = ""
    private var country: String = UNKNOWN_VALUE
    private var city: String = UNKNOWN_VALUE
    private var address: String = UNKNOWN_VALUE

    private var loadingSuggestionsTask: Job = Job()


    init {
        enabledButtonMutableLiveData.postValue(true)
    }

    fun setData(address: String?,  openFragment: () -> Unit){
        this.address = address ?: UNKNOWN_VALUE
        if(validateEmptyValue(this.address)) {
            val addres = Address(this.address)
            addressUseCase.setAddress(addres)
            openFragment.invoke()
        }
    }

    fun validateEmptyValue(address: String): Boolean {
        return if (address.isEmpty()) {
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


    fun loadSuggestions(input: String) {
        if (input == prevQuery) {
            return
        }
        prevQuery = input

        loadingSuggestionsTask.cancel()
        progresService.showLoadingDialog()
        loadingSuggestionsTask = viewModelScope.launch {
            delay(2000)
            try {
                withContext(Dispatchers.IO) { repository.getSuggestions(input) }
                    .takeIf { it.isSuccess }
                    ?.let {
                        progresService.hideLoading()
                        mSuggestionsMutableLiveData.value =
                            it.getOrNull()
                                ?.suggestions
                                ?.filter { s -> s.value != input }
                                ?.mapNotNull { s -> s.value?.let { v -> Suggestion(v) } }
                                ?: emptyList()
                    } ?: let {
                    progresService.hideLoading()
                    errorService.show("Ошибка загрузки")

                }
            } catch (t: Throwable) {
                progresService.hideLoading()

            }
        }
    }

}