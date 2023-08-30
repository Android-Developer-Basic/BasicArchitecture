package ru.otus.basicarchitecture.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.WizardCache
import ru.otus.basicarchitecture.viewState.viewStateFR3
import javax.inject.Inject

@HiltViewModel
open class viewModelFR3 @Inject constructor(private val cache: WizardCache): ViewModel() {

    val _stateFR3: MutableLiveData<viewStateFR3> = MutableLiveData<viewStateFR3>(  //хранит состояние (viewModel)
        viewStateFR3(cache.tags) // Начальное значение
    )

    val viewState: LiveData<viewStateFR3> get() = _stateFR3.distinctUntilChanged()

    private fun renderView() {   //обновлялка viewState (синхронизация с WizardCache)
        _stateFR3.value = with(cache) {
            viewStateFR3(tags)
        }
    }

    fun toggleTag(name: String) {
        val currentValue = cache.tags[name]
        if (null == currentValue) {
            // Проверяем, что идем по заданным тэгам, а не от балды
            return
        }
        // Меняем значение на противоположное
        cache.tags = cache.tags.plus(name to currentValue.not())
        renderView()
    }
}