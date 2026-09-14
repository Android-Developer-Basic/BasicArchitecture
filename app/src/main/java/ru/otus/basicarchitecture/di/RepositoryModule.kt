package ru.otus.basicarchitecture.di

import dagger.Binds
import dagger.Module
import javax.inject.Singleton
import ru.otus.basicarchitecture.address.AddressSuggestionsRepository
import ru.otus.basicarchitecture.address.DaDataAddressSuggestionsRepository

@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAddressSuggestionsRepository(
        implementation: DaDataAddressSuggestionsRepository,
    ): AddressSuggestionsRepository
}