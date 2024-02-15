package ru.otus.basicarchitecture.presentation.FourthScreen

import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.domain.Model.Address
import ru.otus.basicarchitecture.domain.Model.Interests
import ru.otus.basicarchitecture.domain.Model.Person
import ru.otus.basicarchitecture.domain.getData.GetInfoPersonUseCase
import javax.inject.Inject

class FourthScreenViewModel @Inject constructor(
    info: GetInfoPersonUseCase
) : ViewModel() {

    val modelFourthScreen: ModelFourthScreen

    init {
        val information = info.getInfo()
        val person = information["person"] as Person
        val address = information["address"] as Address
        val interest = information["interests"] as Interests
        modelFourthScreen = ModelFourthScreen(
            person.firstName,
            person.surName,
            person.dateOfBirth,
            "${address.country}, ${address.city}, ${address.city}",
            interest.interests.split(",")
        )
    }
}