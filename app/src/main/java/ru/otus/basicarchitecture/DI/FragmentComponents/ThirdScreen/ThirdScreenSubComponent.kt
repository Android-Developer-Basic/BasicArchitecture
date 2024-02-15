package ru.otus.basicarchitecture.DI.FragmentComponents.ThirdScreen

import dagger.Subcomponent
import ru.otus.basicarchitecture.DI.FragmentComponents.FragmentScope
import ru.otus.basicarchitecture.presentation.ThirdScreen.ThirdScreenFragment

@FragmentScope
@Subcomponent(modules = [ThirdViewModelModule::class])
interface ThirdScreenSubComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build() : ThirdScreenSubComponent
    }

    fun inject(fragmentThird: ThirdScreenFragment)


}