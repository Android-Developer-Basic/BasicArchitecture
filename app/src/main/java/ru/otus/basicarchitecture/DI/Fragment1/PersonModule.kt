package ru.otus.basicarchitecture.DI.FragmentComponents.FirstScreen

import dagger.Module
import dagger.Provides
import ru.otus.basicarchitecture.Core.Model.Person
import ru.otus.basicarchitecture.DI.FragmentScope


@Module
class PersonModule {
    @FragmentScope
    @Provides
    fun providePerson(): Person {
        return Person.defaultPerson()
    }
}

