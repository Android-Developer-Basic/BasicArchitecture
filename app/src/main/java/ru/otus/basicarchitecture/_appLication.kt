package ru.otus.basicarchitecture

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class _appLication: Application() {   //класс процесса приложения, везде есть доступ то него. Создается один на все приложение
}