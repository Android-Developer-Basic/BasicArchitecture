package ru.otus.basicarchitecture.DI.FragmentComponents.Fragment4

import dagger.Subcomponent
import ru.otus.basicarchitecture.DI.FragmentScope
import ru.otus.basicarchitecture.Ui.Fragment4.Fragment4

@FragmentScope
@Subcomponent(modules = [Fragment4ViewModelModule::class])
interface Fragment4SubComponent {


    @Subcomponent.Builder
    interface Builder {
        fun build() : Fragment4SubComponent
    }

    fun inject(fragment: Fragment4)

}