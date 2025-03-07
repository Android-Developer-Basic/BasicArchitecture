package ru.otus.basicarchitecture.ui.address

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.ui.data.AddressData
import ru.otus.basicarchitecture.ui.data.AddressIP
import ru.otus.basicarchitecture.ui.data.RetrofitClient
import ru.otus.basicarchitecture.ui.data.WizardCache
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    var wizardCache: WizardCache,
    private var addressData: AddressData,
    private var retrofitClient:RetrofitClient

    ) : ViewModel() {

    var addressDataArray: MutableList<String> = mutableListOf()
    private var postDataJob: Job? = null

    fun setAddress(address: String) {
        wizardCache.personAddress = address
    }

    fun getDataNotice(address: String) {
        addressData.query = address
        postDataJob?.cancel()

        postDataJob = viewModelScope.launch(Dispatchers.IO) {

            try {
                val result = retrofitClient.apiService.postData(
                    dataModal = addressData
                )
                if (result.isSuccessful) {
                    result.body()?.let {
                        it.suggestions.forEach { address ->
                            addressDataArray.add(address.value)
                        }
                    }
                }

            } catch (e: Exception) {
                Log.d("my", e.toString())
            }
        }
    }
}