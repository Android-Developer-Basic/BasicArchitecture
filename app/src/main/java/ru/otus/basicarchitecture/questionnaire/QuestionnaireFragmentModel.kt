package ru.otus.basicarchitecture.questionnaire

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.otus.basicarchitecture.wizardCache.RegistrationData
import ru.otus.basicarchitecture.wizardCache.WizardCache
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class QuestionnaireFragmentModel @Inject constructor(cache: WizardCache) : ViewModel() {
    val viewState: StateFlow<QuestionnaireModelState> = cache.state.map { render(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, render(cache.state.value))

    private fun dateConverter(date: Date?): String {
        return if (date != null) {
            SimpleDateFormat("dd.MM.yyyy", Locale.UK).format(date.time)
        } else ""
    }

    private fun render(data: RegistrationData) =
        QuestionnaireModelState(
            data.name,
            data.surname,
            dateConverter(data.birthday),
            data.address,
            data.selectedInterests
        )


}

