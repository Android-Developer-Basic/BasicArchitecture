package ru.otus.basicarchitecture.service.impl

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.service.DaDataService
import ru.otus.basicarchitecture.service.dto.DaDataSuggestionsRequest
import ru.otus.basicarchitecture.service.dto.DaDataSuggestionsResponse
import java.util.concurrent.TimeUnit

interface DaDataServiceImpl : DaDataService {

    @POST("suggestions/api/4_1/rs/suggest/address")
    override suspend fun getSuggestions(
        @Body body: DaDataSuggestionsRequest
    ): Response<DaDataSuggestionsResponse>

    companion object {

        fun create(apiKey: String): DaDataService {
            val okHttp = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(Interceptor { chain ->
                    chain.proceed(
                        chain.request().newBuilder()
                            .header("Content-Type", "application/json")
                            .header("Accept", "application/json")
                            .header(
                                "Authorization",
                                "Token $apiKey")
                            .build())
                })
                .build()

            val json = Json {
                coerceInputValues = true
                ignoreUnknownKeys = true
            }

            val retrofit = Retrofit.Builder()
                .client(okHttp)
                .baseUrl("http://suggestions.dadata.ru/")
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()

            return retrofit.create(DaDataServiceImpl::class.java)
        }
    }

}