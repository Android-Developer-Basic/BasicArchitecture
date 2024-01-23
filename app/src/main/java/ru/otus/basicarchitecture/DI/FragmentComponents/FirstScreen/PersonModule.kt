package ru.otus.basicarchitecture.DI.FragmentComponents.FirstScreen

import dagger.Module
import dagger.Provides
import ru.otus.basicarchitecture.DI.FragmentComponents.FragmentScope
import ru.otus.basicarchitecture.domain.Model.Person

@Module
class PersonModule {
    @FragmentScope
    @Provides
    fun providePerson(): Person{
        return Person.defaultPerson()
    }
}