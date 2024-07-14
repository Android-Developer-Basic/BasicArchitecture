package ru.otus.basicarchitecture.DI.MainComponent

import dagger.Module
import dagger.Provides
import ru.otus.basicarchitecture.data.WizardCache
import javax.inject.Singleton

@Module
class WizardCacheModule {
    @Singleton
    @Provides
    fun provideWizardCache() : WizardCache{
        return WizardCache(
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        )
    }
}