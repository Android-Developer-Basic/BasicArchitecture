package ru.otus.basicarchitecture.DI.FragmentComponents.ThirdScreen

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ru.otus.basicarchitecture.presentation.ThirdScreen.ThirdScreenViewModel

@Module
interface ThirdViewModelModule {

    @IntoMap
    @StringKey("ThirdScreenViewModel")
    @Binds
    fun bindThirdScreenViewModel(impl: ThirdScreenViewModel): ViewModel

}