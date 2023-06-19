package ru.otus.basicarchitecture.presentation.viewmodels

import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.domain.models.HobbyItem
import ru.otus.basicarchitecture.domain.usecases.data_use_cases.SaveDataUseCase
import ru.otus.basicarchitecture.domain.usecases.user_hobby_list_use_cases.GetHobbyListUseCase
import ru.otus.basicarchitecture.domain.usecases.user_hobby_list_use_cases.SetHobbyUseCase

class Fragment3ViewModel(
    private val getHobbyListUseCase: GetHobbyListUseCase,
    private val setHobbyUseCase: SetHobbyUseCase,
    private val saveDataUseCase: SaveDataUseCase
):ViewModel() {

    private val mutableData = getHobbyListUseCase.getHobbyList()
    val data get() = mutableData



    fun changeEnableState(hobbyItem: HobbyItem){
        setHobbyUseCase.setHobby(hobbyItem)
    }

}