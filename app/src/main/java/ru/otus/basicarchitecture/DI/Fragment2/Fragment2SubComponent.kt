package ru.otus.basicarchitecture.DI.Fragment2

import dagger.Subcomponent
import ru.otus.basicarchitecture.DI.FragmentComponents.FirstScreen.PersonModule
import ru.otus.basicarchitecture.DI.FragmentScope
import ru.otus.basicarchitecture.Ui.Fragment1.Fragment2

@FragmentScope
@Subcomponent(modules = [AddressModule::class, Fragment2ViewModelModule::class])
interface Fragment2SubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build() : Fragment2SubComponent
    }

    fun inject(fragment2: Fragment2)
}