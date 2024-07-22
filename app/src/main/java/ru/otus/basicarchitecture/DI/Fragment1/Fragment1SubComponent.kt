package ru.otus.basicarchitecture.DI.Fragment1

import dagger.Subcomponent
import ru.otus.basicarchitecture.DI.FragmentComponents.FirstScreen.PersonModule
import ru.otus.basicarchitecture.DI.FragmentScope
import ru.otus.basicarchitecture.Ui.Fragment1.Fragment1

@FragmentScope
@Subcomponent(modules = [PersonModule::class, Fragment1ViewModelModule::class])
interface Fragment1SubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build() : Fragment1SubComponent
    }

    fun inject(fragment1: Fragment1)
}