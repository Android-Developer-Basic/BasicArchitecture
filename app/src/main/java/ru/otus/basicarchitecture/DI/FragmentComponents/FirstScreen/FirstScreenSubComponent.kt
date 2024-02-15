package ru.otus.basicarchitecture.DI.FragmentComponents.FirstScreen

import dagger.Subcomponent
import ru.otus.basicarchitecture.DI.FragmentComponents.FragmentScope
import ru.otus.basicarchitecture.presentation.FirstScreen.FirstScreenFragment


/**
 * Подкомпонент для первого фрагмента
 */
@FragmentScope
@Subcomponent(modules = [PersonModule::class, FirstViewModelModule::class])
interface FirstScreenSubComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build() : FirstScreenSubComponent
    }

    fun inject(fragmentFirst: FirstScreenFragment)
}