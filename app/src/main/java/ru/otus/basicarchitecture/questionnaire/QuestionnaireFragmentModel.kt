package ru.otus.basicarchitecture.questionnaire

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.DataCacheStorage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class QuestionnaireFragmentModel @Inject constructor(dataCacheStorage: DataCacheStorage): ViewModel() {
    private val _state = MutableLiveData<QuestionnaireModelState>()
    val questionnaireState: LiveData<QuestionnaireModelState> = _state

    private fun dateConverter(date : Date?): String {
        return if (date != null) {
            SimpleDateFormat("dd.MM.yyyy", Locale.UK).format(date.time)
        } else ""
    }

    init {
        _state.postValue(
            QuestionnaireModelState(
                dataCacheStorage.cache.value?.name ?: "",
                dataCacheStorage.cache.value?.surname ?: "",
                dateConverter(dataCacheStorage.cache.value?.birthday),
                "${dataCacheStorage.cache.value?.country} ${dataCacheStorage.cache.value?.city} ${dataCacheStorage.cache.value?.address}",
                dataCacheStorage.cache.value?.selectedInterests ?: emptyList()
            )
        )
    }


}

