package ru.otus.basicarchitecture.DI.MainComponent

import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import me.amitshekhar.mvvm.di.ApplicationContext
import me.amitshekhar.mvvm.di.BaseUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.otus.basicarchitecture.App
import ru.otus.basicarchitecture.Core.Utils.AppConstant
import ru.otus.basicarchitecture.data.NetworkService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: App) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application
    }

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "http://suggestions.dadata.ru/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("Server", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttp = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(Interceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header(
                            "Authorization",
                            "Token ${AppConstant.API_KEY}")
                        .build())
            })
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttp)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }

}