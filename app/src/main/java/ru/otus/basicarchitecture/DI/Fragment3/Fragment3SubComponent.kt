package ru.otus.basicarchitecture.DI.Fragment3

import dagger.Subcomponent
import ru.otus.basicarchitecture.DI.FragmentScope
import ru.otus.basicarchitecture.Ui.Fragment3.Fragment3

@FragmentScope
@Subcomponent(modules = [Fragment3ViewModelModule::class])
interface Fragment3SubComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build() : Fragment3SubComponent
    }

    fun inject(fragmentThird: Fragment3)


}