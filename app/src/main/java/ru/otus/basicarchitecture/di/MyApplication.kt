package ru.otus.basicarchitecture.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import ru.otus.basicarchitecture.repository.WizardCache
import javax.inject.Singleton

@HiltAndroidApp
class MyApplication : Application() {
}

@InstallIn(SingletonComponent::class)
@Module
object CacheModule {
    @Provides
    @Singleton
    fun provideWizardCache(): WizardCache {
        return WizardCache()
    }
}