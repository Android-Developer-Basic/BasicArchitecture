package ru.otus.basicarchitecture.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.data.localbase.UserData
import ru.otus.basicarchitecture.domain.Interests
import ru.otus.basicarchitecture.domain.WizardCacheService
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val wizardCacheService: WizardCacheService
) : ViewModel() {

    private val _userInfo = MutableLiveData<UserData>()
    val userInfo: LiveData<UserData> get() = _userInfo

    private val _interestsList = MutableLiveData<List<Interests>>()
    val interestsList: LiveData<List<Interests>> get() = _interestsList

    fun getUserInfo() {
        val userData = wizardCacheService.getUserInfo()

        _userInfo.value = userData

        val currentInterestsList = _interestsList.value ?: emptyList()
        val updatedList = currentInterestsList + userData.interests
        _interestsList.value = updatedList
    }
}