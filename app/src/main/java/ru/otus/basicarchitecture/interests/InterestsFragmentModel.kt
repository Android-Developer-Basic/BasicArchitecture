package ru.otus.basicarchitecture.interests

import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.DataCache

private const val MIN_INTERESTS_COUNT = 1

class InterestsFragmentModel : ViewModel() {
    val listOfInterests  by lazy{
        DataCache.listOfInterests
    }

    fun interestsChange(checkedChipIds: List<Int>) {
        TODO("Not yet implemented")
    }

    fun checkInterest(count: Int): Boolean {
        return count >= MIN_INTERESTS_COUNT
    }

    fun onNextButtonClicked(checkedChipIds: MutableList<Int>) {
        DataCache.selectedInterests =
            listOfInterests.filterIndexed { index, _ ->  checkedChipIds.contains(index) }
    }

}





