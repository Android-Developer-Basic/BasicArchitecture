package ru.otus.basicarchitecture.data

import ru.otus.basicarchitecture.domain.Model.Address
import ru.otus.basicarchitecture.domain.Model.Interests
import ru.otus.basicarchitecture.domain.Model.Person


class WizardCache {
    private var resultDataModel: ResultDataModel? = null

}


//комментарии на уточнить - не уверен что модель из домаина должна находится в дата слое
//не знаю в каком слое делать парсер
private data class ResultDataModel(
    val person: Person,
    val address: Address,
    val interests: Interests
)