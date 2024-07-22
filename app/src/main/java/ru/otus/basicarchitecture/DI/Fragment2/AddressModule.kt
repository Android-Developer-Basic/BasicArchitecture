package ru.otus.basicarchitecture.DI.Fragment2
import dagger.Module
import dagger.Provides
import ru.otus.basicarchitecture.Core.Model.Address
import ru.otus.basicarchitecture.DI.FragmentScope

@Module
class AddressModule {
    @FragmentScope
    @Provides
    fun providesAddress(): Address {
        return Address("")
    }
}