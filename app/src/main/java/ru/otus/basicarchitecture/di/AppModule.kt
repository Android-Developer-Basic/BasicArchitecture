package ru.otus.basicarchitecture.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.otus.basicarchitecture.domain.usecases.data_use_cases.GetDataUseCase
import ru.otus.basicarchitecture.domain.usecases.user_hobby_list_use_cases.GetHobbyListUseCase
import ru.otus.basicarchitecture.domain.usecases.date_use_cases.PickDateUseCase
import ru.otus.basicarchitecture.domain.usecases.data_use_cases.SaveDataUseCase
import ru.otus.basicarchitecture.domain.usecases.user_hobby_list_use_cases.SetHobbyUseCase
import ru.otus.basicarchitecture.domain.usecases.date_use_cases.ValidationUseCase
import ru.otus.basicarchitecture.presentation.fragments.Fragment1
import ru.otus.basicarchitecture.presentation.fragments.Fragment2
import ru.otus.basicarchitecture.presentation.fragments.Fragment3
import ru.otus.basicarchitecture.presentation.fragments.Fragment4
import ru.otus.basicarchitecture.presentation.viewmodels.ActivityVMFactory
import ru.otus.basicarchitecture.presentation.viewmodels.Fragment1VMFactory
import ru.otus.basicarchitecture.presentation.viewmodels.Fragment2VMFactory
import ru.otus.basicarchitecture.presentation.viewmodels.Fragment3VMFactory
import ru.otus.basicarchitecture.presentation.viewmodels.Fragment4VMFactory
import javax.inject.Singleton


@Module
class AppModule(val context: Context) {

    @Provides

    fun provideContext():Context{
        return context
    }



    @Provides
    fun provideFragment1VMFactory(
        getDataUseCase: GetDataUseCase,
        saveDataUseCase: SaveDataUseCase,
        pickDateUseCase: PickDateUseCase,
        validationUseCase: ValidationUseCase,

        )
    :Fragment1VMFactory{
        return Fragment1VMFactory(
            getDataUseCase,
            saveDataUseCase,
            pickDateUseCase,
            validationUseCase,


        )
    }

    @Provides
    fun provideFragment2VMFactory(
        getDataUseCase: GetDataUseCase,
        saveDataUseCase: SaveDataUseCase,

        )
            :Fragment2VMFactory{
        return Fragment2VMFactory(
            getDataUseCase,
            saveDataUseCase,

        )
    }

    @Provides
    fun provideFragment3VMFactory(
        getHobbyListUseCase: GetHobbyListUseCase,
        setHobbyUseCase: SetHobbyUseCase,
        saveDataUseCase: SaveDataUseCase
    ):Fragment3VMFactory{
        return Fragment3VMFactory(
            getHobbyListUseCase,
            setHobbyUseCase,
            saveDataUseCase
        )
    }

    @Provides
    fun provideFragment4VMFactory(
        getDataUseCase: GetDataUseCase
    ): Fragment4VMFactory {
        return Fragment4VMFactory(
            getDataUseCase
        )
    }

    @Provides
    fun provideActivityVMFactory():ActivityVMFactory{
        return ActivityVMFactory()
    }

    @Provides
    @Singleton
    fun provideFragment1():Fragment1{
        return Fragment1()
    }

    @Provides
    @Singleton
    fun provideFragment2():Fragment2{
        return Fragment2()
    }

    @Provides
    @Singleton
    fun provideFragment3():Fragment3{
        return Fragment3()
    }

    @Provides
    @Singleton
    fun provideFragment4(): Fragment4 {
        return Fragment4()
    }


}

