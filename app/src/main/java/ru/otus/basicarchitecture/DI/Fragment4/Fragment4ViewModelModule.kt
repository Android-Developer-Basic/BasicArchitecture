package ru.otus.basicarchitecture.DI.FragmentComponents.Fragment4

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ru.otus.basicarchitecture.Ui.Fragment4.Fragment4ViewModel


@Module
interface Fragment4ViewModelModule {

    @IntoMap
    @StringKey("Fragment4ViewModel")
    @Binds
    fun bindFourthScreenViewModel(impl: Fragment4ViewModel): ViewModel
}