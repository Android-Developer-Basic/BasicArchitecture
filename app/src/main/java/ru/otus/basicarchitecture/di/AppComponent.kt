package ru.otus.basicarchitecture.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import ru.otus.basicarchitecture.MainActivity

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
    ],
)
interface AppComponent {
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}