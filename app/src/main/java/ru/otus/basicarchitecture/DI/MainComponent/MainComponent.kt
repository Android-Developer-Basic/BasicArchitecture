package ru.otus.basicarchitecture.DI.MainComponent

import dagger.Component
import ru.otus.basicarchitecture.DI.FragmentComponents.FirstScreen.FirstScreenSubComponent
import ru.otus.basicarchitecture.data.WizardCache
import ru.otus.basicarchitecture.domain.Repository
import javax.inject.Singleton

@Singleton
@Component(modules = [WizardCacheModule::class, RepositoryModule::class])
interface MainComponent {
    fun firstScreenSubComponent() : FirstScreenSubComponent.Builder

    fun wizardCache(): WizardCache

    fun repository(): Repository
}