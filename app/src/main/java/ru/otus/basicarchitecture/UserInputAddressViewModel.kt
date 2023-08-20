package ru.otus.basicarchitecture

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInputAddressViewModel @Inject constructor(
    private val wizardCache: WizardCache,
    private val daDataService: DaDataService
) : ViewModel() {
    val validateState = MutableLiveData<ValidateState>()
    val viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState())
    val suggestions = MutableLiveData<List<Suggestion>>()

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun search(query: String) {
        viewModelScope.launch {
            try {
                val response = daDataService.getSuggestions(DaDataRequest(query))
                if (response.isSuccessful) {
                    val data = response.body()
                    suggestions.value = data?.suggestions
                    Log.d("log----------------", "onResponse: ${data?.suggestions}")
                } else {
                    Log.d("log----------------", response.toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("log----------------", "Exception: $e")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }



    fun validateAndSaveAddress() {
        val stats = viewState.value!!
        val checkFiles = isValidFields(
            listOf(
                listOf(stats.address, "Адрес")
            ))

        validateState.value = if (checkFiles.isNotEmpty())
            ValidateState.LoseFiled(checkFiles)
        else {
            wizardCache.address = stats.address
            ValidateState.Ok
        }
    }


    private fun isValidFields(fields: List<List<String>>): String {
        for (field in fields)
            if (field[0].isEmpty()) {
                return field[1]
            }
        return ""
    }
}