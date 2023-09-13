package ru.otus.basicarchitecture

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserInputLastViewModel @Inject constructor(
    val wizardCache: WizardCache
) : ViewModel()