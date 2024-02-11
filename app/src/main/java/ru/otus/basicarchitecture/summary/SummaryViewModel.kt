package ru.otus.basicarchitecture.summary

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import ru.otus.basicarchitecture.data.AddressInformationData
import ru.otus.basicarchitecture.data.InterestsInformationData
import ru.otus.basicarchitecture.data.PersonalInformationData
import ru.otus.basicarchitecture.data.WizardCache
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val cache: WizardCache
) : ViewModel() {
    val addressInfoState: StateFlow<AddressInformationData> = cache.addressInfo
    val personalInfoState: StateFlow<PersonalInformationData> = cache.personalInfo
    val interestInformation: StateFlow<InterestsInformationData> = cache.interestInformation
}