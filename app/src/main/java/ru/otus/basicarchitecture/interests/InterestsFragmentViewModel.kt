package ru.otus.basicarchitecture.interests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.DataCache
import javax.inject.Inject

class InterestsFragmentViewModel @Inject constructor(
    private val dataCache: DataCache
) : ViewModel(){

    val interestsList = listOf(
        "movies",
        "music",
        "food",
        "sport",
        "cars",
        "fashion",
        "games"
    )
    private val _interests = MutableLiveData(interestsList)
    val interests: LiveData<List<String>> = _interests

    private val _selectedInterests = MutableLiveData<MutableList<String>>()
    val selectedInterests: LiveData<MutableList<String>> = _selectedInterests

    fun addOrRemoveInterest(interest: String) {
        val interests = _selectedInterests.value?.toMutableList() ?: mutableListOf()
        if (interests.contains(interest)) {
            interests.remove(interest)
        } else {
            interests.add(interest)
        }
        _selectedInterests.value = interests
    }

    fun save() {
        dataCache.interests = selectedInterests.value ?: listOf()
    }
}