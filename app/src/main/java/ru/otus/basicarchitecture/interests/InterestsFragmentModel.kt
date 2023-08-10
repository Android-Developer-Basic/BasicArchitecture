package ru.otus.basicarchitecture.interests

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.DataCacheStorage
import javax.inject.Inject

private const val MIN_INTERESTS_COUNT = 5
@HiltViewModel
class InterestsFragmentModel @Inject constructor(private val dataCacheStorage: DataCacheStorage): ViewModel() {
    val listOfInterests  by lazy{
        dataCacheStorage.cache.value?.listOfInterests ?: emptyList()
    }

    fun checkInterest(count: Int): Boolean {
        return count >= MIN_INTERESTS_COUNT
    }

    fun onNextButtonClicked(checkedChipIds: MutableList<Int>) {
        dataCacheStorage.cache.value = dataCacheStorage.cache.value?.copy(
            selectedInterests = listOfInterests.filterIndexed { index, _ ->  checkedChipIds.contains(index) }
        )
    }

}





