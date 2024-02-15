package ru.otus.basicarchitecture.presentation.ThirdScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.domain.Model.Interests
import ru.otus.basicarchitecture.domain.getData.GetListInterests
import ru.otus.basicarchitecture.domain.setData.SetInterestsPersonUseCase
import java.util.TreeSet
import javax.inject.Inject

class ThirdScreenViewModel @Inject constructor(
    private val setInterestsPersonUseCase: SetInterestsPersonUseCase,
    getListInterests: GetListInterests
) : ViewModel() {

    private val saveInterests = mutableListOf<ModelInterestsForView>()

    private val mutableListAllInterests = sortedSetOf<ModelInterestsForView>(
        { o1, o2 -> o1.id.compareTo(o2.id) }
    )
    private val _listInterestsLD = MutableLiveData<List<ModelInterestsForView>>()

    val listInterestsLD: LiveData<List<ModelInterestsForView>> = _listInterestsLD

    //todo: разберем наверное чтение с визарда, что бы при переходе экрана туда - сюда , значения не пропадали
    init {
        val aa = getListInterests.getList()
        for (a in aa.indices) {
            val model = ModelInterestsForView(a, aa[a], false)
            mutableListAllInterests.add(model)
        }
        _listInterestsLD.postValue(mutableListAllInterests.toList())
    }

//    private fun updateListSaveInterests(model: ModelInterestsForView){
//        if(model.enabled) saveInterests.add(model) else saveInterests.remove(model)
//    }

    fun setData(openFragment: () -> Unit){

        val list = mutableListOf<String>()
        saveInterests.forEach {
            list.add(it.interests)
        }
        setInterestsPersonUseCase.setInterests(
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