package ru.otus.basicarchitecture.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import ru.otus.basicarchitecture.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val apiKey = BuildConfig.API_KEY

    private const val BASE_URL = "https://suggestions.dadata.ru/suggestions/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    //TODO api key вынести
    private fun okHttpClient(
        apiKey: String = NetworkModule.apiKey
    ) = OkHttpClient().newBuilder()
        .addInterceptor(
            Interceptor { chain ->
                val request: Request = chain.request()
                    .newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("Authorization", "Token $apiKey")
                    .build()
                chain.proceed(request)
            }
        )

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient().build())
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideAddressHintService(retrofit: Retrofit): AddressApi {
        return retrofit.create(AddressApi::class.java)
    }
}