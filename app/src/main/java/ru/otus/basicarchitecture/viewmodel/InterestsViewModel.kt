package ru.otus.basicarchitecture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.WizardCache
import ru.otus.basicarchitecture.model.Repository
import javax.inject.Inject

@HiltViewModel
class InterestsViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    @Inject lateinit var wizardCache: WizardCache

    private val resultInterestsLive = MutableLiveData<List<String>>()

    fun loadData () {
        resultInterestsLive.value = repository.getInterests()
    }

    fun saveData(interests :List<String>) {
        wizardCache.saveInterests(interests)
    }

    fun getResultInterestsLive(): LiveData<List<String>?> {
        return resultInterestsLive
    }
}