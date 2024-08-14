package ru.otus.basicarchitecture.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.model.Tag
import ru.otus.basicarchitecture.repository.TagRepository
import ru.otus.basicarchitecture.repository.WizardCache
import javax.inject.Inject

class Fragment1ViewModel @Inject constructor(private val wizardCache: WizardCache) : ViewModel() {

}