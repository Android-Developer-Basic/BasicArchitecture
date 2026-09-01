package ru.otus.basicarchitecture.ui.summary

import androidx.lifecycle.ViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import ru.otus.basicarchitecture.cache.WizardCache
import ru.otus.basicarchitecture.model.WizardData

class SummaryViewModel @Inject constructor(
    wizardCache: WizardCache,
) : ViewModel() {
    val uiState: StateFlow<WizardData> = wizardCache.data
}