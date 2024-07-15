package ru.otus.basicarchitecture.DI.Fragment3

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ru.otus.basicarchitecture.Ui.Fragment3.Fragment3ViewModel

@Module
interface Fragment3ViewModelModule {

    @IntoMap
    @StringKey("Fragment3ViewModel")
    @Binds
    fun bindThirdScreenViewModel(impl: Fragment3ViewModel): ViewModel

}