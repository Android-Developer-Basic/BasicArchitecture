package ru.otus.basicarchitecture.presentation.ThirdScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.domain.Model.Interests
import ru.otus.basicarchitecture.domain.getData.GetListInterests
import ru.otus.basicarchitecture.domain.setData.SetInterestsPersonUseCase
import javax.inject.Inject

class ThirdScreenViewModel @Inject constructor(
    private val setInterestsPersonUseCase: SetInterestsPersonUseCase,
    getListInterests: GetListInterests
) : ViewModel() {

    private val saveInterests = mutableListOf<ModelInterestsForView>()

    private val mutableListAllInterests: MutableList<ModelInterestsForView>
    private val _listInterestsLD = MutableLiveData<List<ModelInterestsForView>>()

    val listInterestsLD: LiveData<List<ModelInterestsForView>> = _listInterestsLD

    //todo: разберем наверное чтение с визарда, что бы при переходе экрана туда - сюда , значения не пропадали
    init {
        val aa = getListInterests.getList()
        mutableListAllInterests =
            aa.mapIndexed{
                    index, s -> ModelInterestsForView(index,s,false)
            }.toMutableList()
        _listInterestsLD.postValue(mutableListAllInterests)
    }

    private fun updateListSaveInterests(model: ModelInterestsForView){
        if(model.enabled) saveInterests.add(model) else saveInterests.remove(model)
    }


/*    private fun setupState(){
        saveInterests.forEach{
            mutableListAllInterests[it.id].enabled = it.enabled
        }
    }*/

    fun setData( openFragment: () -> Unit){
        setInterestsPersonUseCase.setInterests(Interests(saveInterests.toString()))
    }

    fun changeEnabledState(model: ModelInterestsForView){
        val newEnabledState = !model.enabled
        mutableListAllInterests[model.id].enabled = newEnabledState
        updateListSaveInterests(mutableListAllInterests[model.id])
        updateList()
    }

    private fun updateList(){
        _listInterestsLD.postValue(mutableListAllInterests)
    }

}