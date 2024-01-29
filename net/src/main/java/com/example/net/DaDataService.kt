package com.example.net

import com.example.net.data.AddressRequest
import com.example.net.data.AddressResponse
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
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://suggestions.dadata.ru/"

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

    @Provides
    @Singleton
    fun retrofit(): Retrofit = Retrofit.Builder()
        .client(okHttp)
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Singleton
    @Provides
    fun provideDaDataApi(retrofit: Retrofit): DaDataApi {
        return retrofit.create(DaDataApi::class.java)
    }
}

interface DaDataApi {
    @POST("/suggestions/api/4_1/rs/suggest/address")
    suspend fun getAddress(@Body requestBody: AddressRequest): Response<AddressResponse>
}

private val interceptor: Interceptor = Interceptor { chain ->
    val request = chain.request().newBuilder()
        .addHeader("Content-Type", "application/json")
        .addHeader("Accept", "application/json")
        .addHeader("Authorization", "Token " + BuildConfig.API_KEY).build()
    chain.proceed(request)
}
