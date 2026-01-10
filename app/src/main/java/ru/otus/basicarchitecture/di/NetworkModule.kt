package ru.otus.basicarchitecture.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.otus.basicarchitecture.BuildConfig
import ru.otus.basicarchitecture.data.network.DadataApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder().header(
                "Authorization", "Token ${BuildConfig.DADATA_API_KEY}"
            ).header("Content-Type", "application/json").header("Accept", "application/json")
                .method(original.method, original.body).build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder().addInterceptor(authInterceptor).addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://suggestions.dadata.ru/")
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideDadataApi(retrofit: Retrofit): DadataApi {
        return retrofit.create(DadataApi::class.java)
    }
}