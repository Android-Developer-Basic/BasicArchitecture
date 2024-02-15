package ru.otus.basicarchitecture.DI.FragmentComponents.FourthScreen

import dagger.Subcomponent
import ru.otus.basicarchitecture.DI.FragmentComponents.FragmentScope
import ru.otus.basicarchitecture.presentation.FourthScreen.FourthScreenFragment

@FragmentScope
@Subcomponent(modules = [FourthViewModelModule::class])
interface FourthScreenSubComponent {


    @Subcomponent.Builder
    interface Builder {
        fun build() : FourthScreenSubComponent
    }

    fun inject(fragment: FourthScreenFragment)

}