package ru.otus.basicarchitecture.di

import dagger.Component
import ru.otus.basicarchitecture.presentation.activity.MainActivity
import ru.otus.basicarchitecture.presentation.fragments.Fragment1
import ru.otus.basicarchitecture.presentation.fragments.Fragment2
import ru.otus.basicarchitecture.presentation.fragments.Fragment3
import ru.otus.basicarchitecture.presentation.fragments.Fragment4
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,DataModule::class, DomainModule::class])
interface AppComponent {

    fun injectActivity(activity: MainActivity)

    fun injectFragment1(fragment: Fragment1)

    fun injectFragment2(fragment: Fragment2)

    fun injectFragment3(fragment: Fragment3)

    fun injectFragment4(fragment: Fragment4)

}