package ru.otus.basicarchitecture.di

import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.otus.basicarchitecture.BuildConfig
import ru.otus.basicarchitecture.network.dadata.DaDataApi
import ru.otus.basicarchitecture.network.dadata.DaDataApiKey
import ru.otus.basicarchitecture.network.dadata.DaDataAuthInterceptor

@Module
object NetworkModule {
    @Provides
    @DaDataApiKey
    fun provideDaDataApiKey(): String = BuildConfig.DADATA_API_KEY.trim()

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: DaDataAuthInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://suggestions.dadata.ru/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideDaDataApi(retrofit: Retrofit): DaDataApi = retrofit.create(DaDataApi::class.java)
}