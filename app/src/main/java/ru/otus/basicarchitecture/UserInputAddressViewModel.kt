package ru.otus.basicarchitecture

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserInputAddressViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {
    val validateState = MutableLiveData<ValidateState>()
    val viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState())
    val suggestions = MutableLiveData<List<Suggestion>>()

    fun search(query: String) {
        MyApplication.instance.services.getSuggestions(DaDataRequest(query))
            .enqueue(object : Callback<SuggestionsResponse> {

                override fun onResponse(
                    call: Call<SuggestionsResponse>,
                    response: Response<SuggestionsResponse>
                ) {
                    val data = response.body()
                    suggestions.value = data?.suggestions

                }

                override fun onFailure(call: Call<SuggestionsResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
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