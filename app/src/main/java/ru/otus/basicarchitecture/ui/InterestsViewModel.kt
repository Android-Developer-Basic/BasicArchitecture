package ru.otus.basicarchitecture.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.domain.Interests
import ru.otus.basicarchitecture.domain.WizardCacheService
import javax.inject.Inject

@HiltViewModel
class InterestsViewModel @Inject constructor(
    private val wizardCacheService: WizardCacheService
) : ViewModel() {

    private val interestsList = mutableListOf<Interests>()

    fun updateInterestsList(checkedIds: List<Int>) {
        interestsList.clear()
        checkedIds.mapNotNull { id ->
            when (id) {
                R.id.music -> Interests.MUSIC
                R.id.museums -> Interests.MUSEUMS
                R.id.modern_art -> Interests.MODERN_ART
                R.id.cars -> Interests.CARS
                R.id.bicycles -> Interests.BICYCLES
                R.id.parties -> Interests.PARTIES
                R.id.theatre -> Interests.THEATRE
                R.id.movies -> Interests.MOVIES
                R.id.gardening -> Interests.GARDENING
                R.id.hiking -> Interests.HIKING
                R.id.literature -> Interests.LITERATURE
                R.id.craft -> Interests.CRAFT
                else -> null
            }
        }.let { interestsList.addAll(it) }
    }

    fun updateUserInterests() {
        wizardCacheService.updateUserInterests(interests = interestsList)
    }
}