package ru.otus.basicarchitecture.di

import dagger.Module
import dagger.Provides
import ru.otus.basicarchitecture.domain.repository.DatePicker
import ru.otus.basicarchitecture.domain.repository.HobbyRepository
import ru.otus.basicarchitecture.domain.repository.UserRepository
import ru.otus.basicarchitecture.domain.usecases.user_hobby_list_use_cases.GetHobbyListUseCase
import ru.otus.basicarchitecture.domain.usecases.user_hobby_list_use_cases.SetHobbyUseCase
import ru.otus.basicarchitecture.domain.usecases.data_use_cases.GetDataUseCase
import ru.otus.basicarchitecture.domain.usecases.date_use_cases.PickDateUseCase
import ru.otus.basicarchitecture.domain.usecases.data_use_cases.SaveDataUseCase
import ru.otus.basicarchitecture.domain.usecases.date_use_cases.ValidationUseCase

@Module
class DomainModule {

    @Provides
    fun provideGetDataUseCase(userRepository: UserRepository) = GetDataUseCase(userRepository)

    @Provides
    fun provideSaveDataUseCase(userRepository: UserRepository) = SaveDataUseCase(userRepository)

    @Provides
    fun providePickDateUseCase(datePicker: DatePicker) = PickDateUseCase(datePicker)

    @Provides
    fun provideValidationUseCase(datePicker: DatePicker) = ValidationUseCase(datePicker)

    @Provides
    fun provideGetHobbyListUseCase(hobbyRepository: HobbyRepository) = GetHobbyListUseCase(hobbyRepository)

    @Provides
    fun  provideSetHobbyUseCase(hobbyRepository: HobbyRepository) = SetHobbyUseCase(hobbyRepository)

}