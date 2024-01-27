package ru.otus.basicarchitecture

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.otus.basicarchitecture.service.DaDataService
import ru.otus.basicarchitecture.service.InterestsService
import ru.otus.basicarchitecture.service.impl.DaDataServiceImpl
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

    @Provides
    @Singleton
    fun daDataService(@ApplicationContext context: Context): DaDataService =
        DaDataServiceImpl.create(context.getString(R.string.dadata_api_key))

}