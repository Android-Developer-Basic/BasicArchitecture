package ru.otus.basicarchitecture.app

import android.app.Application
import ru.otus.basicarchitecture.di.AppComponent
import ru.otus.basicarchitecture.di.AppModule
import ru.otus.basicarchitecture.di.DaggerAppComponent


 class App:Application(){
  lateinit var appComponent: AppComponent
  override fun onCreate() {
   super.onCreate()

   appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
  }

 }