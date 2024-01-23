package ru.otus.basicarchitecture.DI.FragmentComponents.FirstScreen

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ru.otus.basicarchitecture.presentation.FirstScreen.FirstScreenViewModel

@Module
interface ViewModelModule {

    @IntoMap
    @StringKey("FirstScreenViewModel")
    @Binds
    fun bindFirstScreenViewModel(impl: FirstScreenViewModel): ViewModel

}