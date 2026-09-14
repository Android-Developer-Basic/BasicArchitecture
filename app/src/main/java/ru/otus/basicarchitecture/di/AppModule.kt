package ru.otus.basicarchitecture.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import java.time.Clock
import javax.inject.Singleton
import ru.otus.basicarchitecture.cache.InMemoryWizardCache
import ru.otus.basicarchitecture.cache.WizardCache
import ru.otus.basicarchitecture.interests.DefaultInterestsRepository
import ru.otus.basicarchitecture.interests.InterestsRepository

@Module
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindWizardCache(implementation: InMemoryWizardCache): WizardCache

    @Binds
    @Singleton
    abstract fun bindInterestsRepository(
        implementation: DefaultInterestsRepository,
    ): InterestsRepository

    companion object {
        @Provides
        @Singleton
        fun provideClock(): Clock = Clock.systemDefaultZone()
    }
}