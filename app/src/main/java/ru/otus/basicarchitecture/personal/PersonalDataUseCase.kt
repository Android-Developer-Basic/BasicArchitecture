package ru.otus.basicarchitecture.personal

import dagger.hilt.android.scopes.ViewModelScoped
import java.time.LocalDate
import javax.inject.Inject

@ViewModelScoped
class PersonalDataUseCase @Inject constructor() {

    fun isBirthDateValid(date: LocalDate) =
        !date.isAfter(LocalDate.now().minusYears(18))

}