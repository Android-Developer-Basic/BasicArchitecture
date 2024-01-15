package ru.otus.basicarchitecture.domain.setData

import ru.otus.basicarchitecture.domain.Model.Address
import ru.otus.basicarchitecture.domain.Repository

class SetAddressUseCase(val repository: Repository) {
    fun setAddress(address: Address){
        repository.setAddress(address)
    }
}