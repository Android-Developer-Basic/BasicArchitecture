package ru.otus.basicarchitecture

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(){
    fun getInterests() :List<String> {
        return mutableListOf<String>("Еда","Работа","Отдых", "Кино", "Спорт")
    }
}