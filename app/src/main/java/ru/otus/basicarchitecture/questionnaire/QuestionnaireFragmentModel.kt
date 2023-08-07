package ru.otus.basicarchitecture.questionnaire

import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.DataCache

class QuestionnaireFragmentModel : ViewModel() {
    val listOfInterests: List<String?> by lazy { DataCache.selectedInterests }

    fun getName(): String {
        return DataCache.name
    }

    fun getSurname(): String {
        return DataCache.surname
    }

    fun getBirthday(): String {
        return DataCache.birthday
    }

    fun getAddress(): String {
        return "${DataCache.country} ${DataCache.city} ${DataCache.address}"
    }
}