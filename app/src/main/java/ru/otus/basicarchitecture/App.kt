package ru.otus.basicarchitecture

import android.app.Application
import ru.otus.basicarchitecture.DI.MainComponent.DaggerMainComponent

class App : Application() {
    val component by lazy {
        DaggerMainComponent.builder().build()
    }
}