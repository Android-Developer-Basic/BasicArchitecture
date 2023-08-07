package ru.otus.basicarchitecture.domain.usecases.date_use_cases

import ru.otus.basicarchitecture.domain.repository.DatePicker

class ValidationUseCase(private val datePicker: DatePicker) {

    fun validation():Boolean{
        return datePicker.validation()
    }
}