@file:Suppress("CAST_NEVER_SUCCEEDS")

package ru.otus.basicarchitecture.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.domain.models.HobbyItem
import ru.otus.basicarchitecture.domain.repository.HobbyRepository



class HobbyRepositoryImpl(context: Context):HobbyRepository {
    private val list = context.resources.getStringArray(R.array.hobbyList)
    private val hobbyList = mutableListOf<HobbyItem>()
    private val data = MutableLiveData<List<HobbyItem>>()
    init {
        for(i in list.indices){
            hobbyList.add(HobbyItem(list[i], false, i))
        }
    }
    override fun getHobbyList(): LiveData<List<HobbyItem>> {
        setData()
        return data
    }

    override fun setHobby(item: HobbyItem) {
        hobbyList.forEach{
            if(it == item)it.enabled = !it.enabled
        }
        getHobbyList()

    }

    private fun setData(){
        data.value = hobbyList.toList()
    }

}