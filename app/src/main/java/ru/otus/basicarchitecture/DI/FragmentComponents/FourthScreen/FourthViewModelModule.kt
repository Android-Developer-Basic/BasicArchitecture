package ru.otus.basicarchitecture.DI.FragmentComponents.FourthScreen

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ru.otus.basicarchitecture.presentation.FourthScreen.FourthScreenViewModel


@Module
interface FourthViewModelModule {

    @IntoMap
    @StringKey("FourthScreenViewModel")
    @Binds
    fun bindFourthScreenViewModel(impl: FourthScreenViewModel): ViewModel
}