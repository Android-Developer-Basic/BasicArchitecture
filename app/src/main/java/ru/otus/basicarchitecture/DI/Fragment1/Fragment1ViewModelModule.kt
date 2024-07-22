package ru.otus.basicarchitecture.DI.Fragment1

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ru.otus.basicarchitecture.Ui.Fragment1.Fragment1ViewModel

@Module
interface Fragment1ViewModelModule {

    @IntoMap
    @StringKey("Fragment1ViewModel")
    @Binds
    fun bindFirstScreenViewModel(impl: Fragment1ViewModel): ViewModel

}