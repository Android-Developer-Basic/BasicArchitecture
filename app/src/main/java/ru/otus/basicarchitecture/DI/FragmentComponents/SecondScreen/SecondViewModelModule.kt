package ru.otus.basicarchitecture.DI.FragmentComponents.SecondScreen

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ru.otus.basicarchitecture.presentation.SecondScreen.SecondScreenViewModel
@Module
interface SecondViewModelModule {

    @IntoMap
    @StringKey("SecondScreenViewModel")
    @Binds
    fun bindFirstScreenViewModel(impl: SecondScreenViewModel): ViewModel

}