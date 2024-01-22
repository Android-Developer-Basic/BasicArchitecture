package com.example.net

import com.example.net.data.AddressDto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://dadata.ru"
@Module
@InstallIn(SingletonComponent::class)
class DaDataService {
    private val okHttp = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()

    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Singleton
    private val retrofit = Retrofit.Builder()
        .client(okHttp)
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val api: DadataApi  get() = retrofit.create(DadataApi ::class.java)

    @Singleton
    @Provides
    fun provideDaDataService(retrofit: Retrofit): DaDataService {
        return retrofit.create(DaDataService::class.java)
    }

    //я не понимаю как связать класс и интерфейс DadataApi запутался всместе с использованием хилта
}

private interface DadataApi  {
    @POST("/suggestions/api/4_1/rs/suggest/address")
    suspend fun getAddress(
        @Query("query") query: String,
        @Query("division") domain: String = "administrative",
        @Query("limit") limit: Int = 5,
        @Query("locale") locale: String = "ru",
    ): Response<AddressDto>
}

private val interceptor: Interceptor = Interceptor { chain ->
    val request = chain.request().newBuilder()
        .addHeader("Content-Type", "application/json")
        .addHeader("Accept", "application/json")
        .addHeader("Authorization", "Token " + BuildConfig.API_KEY).build()
    chain.proceed(request)
}