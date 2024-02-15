package ru.otus.basicarchitecture.DI.FragmentComponents.SecondScreen

import dagger.Module
import dagger.Provides
import ru.otus.basicarchitecture.DI.FragmentComponents.FragmentScope
import ru.otus.basicarchitecture.domain.Model.Address

@Module
class AddressModule {
    @FragmentScope
    @Provides
    fun providesAddress(): Address{
        return Address("","","")
    }


}