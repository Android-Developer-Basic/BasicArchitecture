package ru.otus.basicarchitecture.DI.Fragment2
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ru.otus.basicarchitecture.Ui.Fragment1.Fragment2ViewModel

@Module
interface Fragment2ViewModelModule {

    @IntoMap
    @StringKey("Fragment2ViewModel")
    @Binds
    fun bindFirstScreenViewModel(impl: Fragment2ViewModel): ViewModel

}