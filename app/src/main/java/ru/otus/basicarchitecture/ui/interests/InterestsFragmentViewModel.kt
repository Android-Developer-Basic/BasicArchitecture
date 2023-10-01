package ru.otus.basicarchitecture.ui.interests

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.WizardCacheStorage
import javax.inject.Inject

@HiltViewModel
class InterestsFragmentViewModel @Inject constructor(private val wizardCacheStorage: WizardCacheStorage) : ViewModel() {
    val listOfInterests  by lazy{
        wizardCacheStorage.cache.value?.listOfInterests ?: emptyList()
    }

    fun saveDataToStorage(checkedChipIds: MutableList<Int>) {
        wizardCacheStorage.cache.value = wizardCacheStorage.cache.value?.copy(
            selectedInterests = listOfInterests.filterIndexed { index, _ ->  checkedChipIds.contains(index) }
        )
    }
}