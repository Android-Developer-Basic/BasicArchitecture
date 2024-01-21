package ru.otus.basicarchitecture

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import ru.otus.basicarchitecture.service.InterestsService
import ru.otus.basicarchitecture.service.impl.InterestsServiceStubImpl
import javax.inject.Singleton

@HiltAndroidApp
class BasicArchitectureApplication : Application()

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun interestsService(): InterestsService = InterestsServiceStubImpl()

}