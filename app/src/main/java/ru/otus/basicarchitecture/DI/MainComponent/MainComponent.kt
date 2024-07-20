package ru.otus.basicarchitecture.DI.MainComponent

import android.content.Context
import dagger.Component
import me.amitshekhar.mvvm.di.ApplicationContext
import ru.otus.basicarchitecture.DI.Fragment1.Fragment1SubComponent
import ru.otus.basicarchitecture.DI.Fragment2.Fragment2SubComponent
import ru.otus.basicarchitecture.DI.Fragment3.Fragment3SubComponent
import ru.otus.basicarchitecture.DI.FragmentComponents.Fragment4.Fragment4SubComponent
import ru.otus.basicarchitecture.Domain.Repository
import ru.otus.basicarchitecture.data.NetworkService
import ru.otus.basicarchitecture.data.WizardCache
import javax.inject.Singleton

@Singleton
@Component(modules = [WizardCacheModule::class, RepositoryModule::class, ApplicationModule::class])
interface MainComponent {
    fun fragment1SubComponent() : Fragment1SubComponent.Builder
    fun fragment2SubComponent() : Fragment2SubComponent.Builder
    fun fragment3SubComponent() : Fragment3SubComponent.Builder
    fun fragment4SubComponent() : Fragment4SubComponent.Builder

    fun wizardCache(): WizardCache

    fun repository(): Repository

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService
}