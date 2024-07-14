package ru.otus.basicarchitecture.Ui.Fragment3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.Core.Model.Interests
import ru.otus.basicarchitecture.Domain.Data.InterestsUseCase
import ru.otus.basicarchitecture.presentation.Fragment3.ModelInterestsForView
import javax.inject.Inject

class Fragment3ViewModel @Inject constructor(
    private val interestsUseCase: InterestsUseCase
) : ViewModel() {

    private val saveInterests = mutableListOf<ModelInterestsForView>()

    private val mutableListAllInterests = sortedSetOf<ModelInterestsForView>(
        { o1, o2 -> o1.id.compareTo(o2.id) }
    )
    private val _listInterestsLD = MutableLiveData<List<ModelInterestsForView>>()

    val listInterestsLD: LiveData<List<ModelInterestsForView>> = _listInterestsLD

    init {
        val aa = interestsUseCase.getList()
        for (a in aa.indices) {
            val model = ModelInterestsForView(a, aa[a], false)
            mutableListAllInterests.add(model)
        }
        _listInterestsLD.postValue(mutableListAllInterests.toList())
    }

    fun setData(openFragment: () -> Unit){

        val list = mutableListOf<String>()
        saveInterests.forEach {
            list.add(it.interests)
        }
        interestsUseCase.setInterests(
            Interests(list.joinToString())
        )
        openFragment.invoke()
    }

    fun changeEnabledState(model: ModelInterestsForView) {
        val newModel = model.copy(enabled = !model.enabled)
        mutableListAllInterests.remove(model)
        mutableListAllInterests.add(newModel)
        if(newModel.enabled){
            saveInterests.add(newModel)
        }else{
            saveInterests.remove(newModel)
        }
        updateList()
    }

    private fun updateList() {
        _listInterestsLD.postValue(mutableListAllInterests.toList())
    }

}