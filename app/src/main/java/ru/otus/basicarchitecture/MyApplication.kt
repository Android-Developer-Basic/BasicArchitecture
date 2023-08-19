package ru.otus.basicarchitecture

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://suggestions.dadata.ru/"

@HiltAndroidApp
class MyApplication : Application(){

    lateinit var services: DaDataService

    override fun onCreate() {
        super.onCreate()
        instance = this
        initRetrofit()
    }

    private fun initRetrofit() {
        val client = OkHttpClient.Builder()
            .build()

        val retrofitServices = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        services = retrofitServices.create(DaDataService::class.java)

    }

    companion object {

        lateinit var instance: MyApplication
    }
}