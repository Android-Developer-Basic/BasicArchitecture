package ru.otus.basicarchitecture.Domain.Data

import ru.otus.basicarchitecture.Core.Model.Address
import ru.otus.basicarchitecture.Domain.Repository
import javax.inject.Inject

class AddressUseCase @Inject constructor(
    private val repository: Repository
) {
    fun setAddress(address: Address){
        repository.setAddress(address)
    }
}