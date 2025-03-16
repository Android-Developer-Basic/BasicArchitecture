package ru.otus.basicarchitecture.presentation

import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.data.WizardCache
import ru.otus.basicarchitecture.utils.Utils
import java.util.Date
import javax.inject.Inject

class SummaryViewModel @Inject constructor(private val wizardCache: WizardCache) : ViewModel() {

    val name = wizardCache.name
    val surname = wizardCache.surname
    val birthDate: String = Utils.dateFormatter.format(Date(wizardCache.birthDate))
    val address = "${wizardCache.address}, ${wizardCache.city}, ${wizardCache.country}"
    val interests: List<String> get() = wizardCache.interests
}