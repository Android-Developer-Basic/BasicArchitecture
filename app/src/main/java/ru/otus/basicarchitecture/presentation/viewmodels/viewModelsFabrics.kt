@file:Suppress("UNCHECKED_CAST")

package ru.otus.basicarchitecture.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.otus.basicarchitecture.domain.usecases.data_use_cases.GetDataUseCase
import ru.otus.basicarchitecture.domain.usecases.user_hobby_list_use_cases.GetHobbyListUseCase
import ru.otus.basicarchitecture.domain.usecases.date_use_cases.PickDateUseCase
import ru.otus.basicarchitecture.domain.usecases.data_use_cases.SaveDataUseCase
import ru.otus.basicarchitecture.domain.usecases.user_hobby_list_use_cases.SetHobbyUseCase
import ru.otus.basicarchitecture.domain.usecases.date_use_cases.ValidationUseCase
import ru.otus.basicarchitecture.domain.usecases.network_use_cases.GetNetworkResponseUseCase
import ru.otus.basicarchitecture.domain.usecases.network_use_cases.SendNetworkRequestUseCase

class Fragment1VMFactory(
    private val getDataUseCase: GetDataUseCase,
    private val saveDataUseCase: SaveDataUseCase,
    private val pickDateUseCase: PickDateUseCase,
    private val validationUseCase: ValidationUseCase,
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return Fragment1ViewModel(
            getDataUseCase,
            saveDataUseCase,
            pickDateUseCase,
            validationUseCase,
        ) as T
    }
}

class Fragment2VMFactory(
    private val getDataUseCase: GetDataUseCase,
    private val saveDataUseCase: SaveDataUseCase,
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return Fragment2ViewModel(
            getDataUseCase, saveDataUseCase

        ) as T
    }
}

class Fragment3VMFactory(
    private val getHobbyListUseCase: GetHobbyListUseCase,
    private val setHobbyUseCase: SetHobbyUseCase,
    private val saveDataUseCase: SaveDataUseCase
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return Fragment3ViewModel(
            getHobbyListUseCase, setHobbyUseCase, saveDataUseCase

        ) as T
    }
}

class Fragment4VMFactory(
    private val getDataUseCase: GetDataUseCase
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return Fragment4ViewModel(
            getDataUseCase

        ) as T
    }
}

class UserAddressFragmentVMFactory(
    private val getDataUseCase: GetDataUseCase,
    private val saveDataUseCase: SaveDataUseCase,
    private val getNetworkResponseUseCase: GetNetworkResponseUseCase,
    private val sendNetworkRequestUseCase: SendNetworkRequestUseCase
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserAddressFragmentViewModel(
            getDataUseCase,
            saveDataUseCase,
            getNetworkResponseUseCase,
            sendNetworkRequestUseCase
        ) as T
    }
}




class ActivityVMFactory(): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ActivityViewModel() as T
    }
}
