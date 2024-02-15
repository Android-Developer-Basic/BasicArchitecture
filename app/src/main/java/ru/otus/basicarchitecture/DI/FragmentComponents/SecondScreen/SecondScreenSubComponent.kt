package ru.otus.basicarchitecture.DI.FragmentComponents.SecondScreen

import dagger.Subcomponent
import ru.otus.basicarchitecture.DI.FragmentComponents.FragmentScope
import ru.otus.basicarchitecture.presentation.SecondScreen.SecondScreenFragment
import ru.otus.basicarchitecture.presentation.SecondScreen.SecondScreenViewModel


@FragmentScope
@Subcomponent(modules = [AddressModule::class, SecondViewModelModule::class])
interface SecondScreenSubComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build() : SecondScreenSubComponent
    }

    fun inject(fragmentSecond: SecondScreenFragment)
}