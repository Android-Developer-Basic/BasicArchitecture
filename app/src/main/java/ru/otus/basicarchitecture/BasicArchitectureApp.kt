package ru.otus.basicarchitecture

import android.app.Application
import ru.otus.basicarchitecture.di.AppComponent
import ru.otus.basicarchitecture.di.DaggerAppComponent

class BasicArchitectureApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}