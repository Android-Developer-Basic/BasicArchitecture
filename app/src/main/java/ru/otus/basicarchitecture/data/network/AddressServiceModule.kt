package ru.otus.basicarchitecture.data.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.otus.basicarchitecture.domain.AddressService

@Module
@InstallIn(SingletonComponent::class)
abstract class AddressServiceModule {

    @Binds
    abstract fun bindAddressService(impl: AddressServiceImpl): AddressService
}