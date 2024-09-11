package ru.otus.basicarchitecture.presentation.viewModel

import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.data.WizardCache
import javax.inject.Inject

class SummaryViewModel @Inject constructor(private val wizardCache: WizardCache) : ViewModel() {

    val name = wizardCache.name
    val surname = wizardCache.surname
    val dateOfBirth = wizardCache.dateOfBirth
    val interests: List<String> get() = wizardCache.interests

    val fullAddress =
        listOf(wizardCache.country, wizardCache.city, wizardCache.address).joinToString(" ")
}