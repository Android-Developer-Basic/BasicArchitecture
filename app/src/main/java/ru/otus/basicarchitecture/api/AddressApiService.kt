package ru.otus.basicarchitecture.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.otus.basicarchitecture.BuildConfig
import ru.otus.basicarchitecture.model.AddressSuggestionRequest
import ru.otus.basicarchitecture.model.DadataResponse
import java.util.concurrent.TimeUnit


interface AddressApi {
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("suggestions/api/4_1/rs/suggest/address")
    suspend fun getAddressSuggestions(@Body request: AddressSuggestionRequest): DadataResponse
}

@Module
@InstallIn(SingletonComponent::class)
object AddressApiService {
    private const val BASE_URL = "https://suggestions.dadata.ru/"
    private const val AUTH_KEY = BuildConfig.DADATA_API_KEY

    private val headerInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Token $AUTH_KEY")
            .build()
        chain.proceed(request)
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()


    @get:Provides
    val api: AddressApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AddressApi::class.java)
}