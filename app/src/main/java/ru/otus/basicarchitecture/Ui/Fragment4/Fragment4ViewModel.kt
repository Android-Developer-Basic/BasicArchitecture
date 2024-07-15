package ru.otus.basicarchitecture.Ui.Fragment4

import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.Core.Model.Address
import ru.otus.basicarchitecture.Core.Model.Interests
import ru.otus.basicarchitecture.Core.Model.Person
import ru.otus.basicarchitecture.Domain.Data.PersonUseCase
import ru.otus.basicarchitecture.Ui.Fragment4.Fragment4Model
import javax.inject.Inject

class Fragment4ViewModel @Inject constructor(
    info: PersonUseCase
) : ViewModel() {

    val fragment4Model: Fragment4Model

    init {
        val information = info.getPerson()
        val person = information["person"] as Person
        val address = information["address"] as Address
        val interest = information["interests"] as Interests
        fragment4Model = Fragment4Model(
            person.firstName,
            person.surName,
            person.dateOfBirth,
            "${address.country}, ${address.city}, ${address.city}",
            interest.interests.split(",")
        )
    }
}