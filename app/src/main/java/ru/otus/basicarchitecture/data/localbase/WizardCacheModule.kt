package ru.otus.basicarchitecture.data.localbase

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.otus.basicarchitecture.domain.WizardCacheService

@Module
@InstallIn(SingletonComponent::class)
abstract class WizardCacheModule {

    @Binds
    abstract fun bindWizardCacheService(impl: WizardCacheServiceImpl): WizardCacheService
}