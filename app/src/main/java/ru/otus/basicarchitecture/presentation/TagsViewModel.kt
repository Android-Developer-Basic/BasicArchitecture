package ru.otus.basicarchitecture.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.data.TagsList
import ru.otus.basicarchitecture.data.WizardCache
import javax.inject.Inject

class TagsViewModel @Inject constructor(
    private val wizardCache: WizardCache,
    tagsList: TagsList
) : ViewModel() {
    val tags = tagsList.interests
    private val _interests = MutableLiveData<MutableList<String>>(mutableListOf())
    val interests: LiveData<MutableList<String>> = _interests


    fun selectInterest(interest: String) {
        val interests = _interests.value ?: mutableListOf()
        if (interests.contains(interest)) {
            interests.remove(interest)
        } else {
            interests.add(interest)
        }
        _interests.value = interests
    }
    fun saveToCash() {
        wizardCache.interests = interests.value ?: listOf()
    }
}