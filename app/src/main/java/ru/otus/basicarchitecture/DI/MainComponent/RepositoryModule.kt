package ru.otus.basicarchitecture.DI.MainComponent

import dagger.Binds
import dagger.Module
import ru.otus.basicarchitecture.data.RepositoryImpl
import ru.otus.basicarchitecture.domain.Repository
import javax.inject.Singleton

@Module
interface RepositoryModule {
    @Singleton
    @Binds
    fun getRepository(impl: RepositoryImpl) : Repository
}