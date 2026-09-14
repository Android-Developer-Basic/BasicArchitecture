package ru.otus.basicarchitecture.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.otus.basicarchitecture.ui.address.AddressViewModel
import ru.otus.basicarchitecture.ui.interests.InterestsViewModel
import ru.otus.basicarchitecture.ui.personal.PersonalViewModel
import ru.otus.basicarchitecture.ui.summary.SummaryViewModel

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PersonalViewModel::class)
    abstract fun bindPersonalViewModel(viewModel: PersonalViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddressViewModel::class)
    abstract fun bindAddressViewModel(viewModel: AddressViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InterestsViewModel::class)
    abstract fun bindInterestsViewModel(viewModel: InterestsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SummaryViewModel::class)
    abstract fun bindSummaryViewModel(viewModel: SummaryViewModel): ViewModel
}