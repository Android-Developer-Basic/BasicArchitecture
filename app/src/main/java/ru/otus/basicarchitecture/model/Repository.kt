package ru.otus.basicarchitecture.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(){
    fun getInterests() :List<String> {
        return mutableListOf<String>("Спать","Есть","Отдыхать", "Загорать", "Смотреть на воду")
    }
}