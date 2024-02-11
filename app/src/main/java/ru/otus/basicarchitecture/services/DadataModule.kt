package ru.otus.basicarchitecture.services

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import ru.otus.basicarchitecture.R
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Serializable
data class DaDataSuggestionsRequest(
    val query: String
)

@Serializable
data class DaDataSuggestionsResponse(
    val suggestions: List<Suggestion>?
)

@Serializable
data class Suggestion(
    val value: String?
)

@Module
@InstallIn(SingletonComponent::class)
class DadataModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext appContext: Context): OkHttpClient {
        return OkHttpClient.Builder()
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
                            "Token ${appContext.getString(R.string.dadata_api_key)}")
                        .build())
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val json = Json {
            coerceInputValues = true
            ignoreUnknownKeys = true
        }

        return Retrofit.Builder()
            .baseUrl("http://suggestions.dadata.ru/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): DadataApiService {
        return retrofit.create(DadataApiService::class.java)
    }
}

interface DadataApiService {
    @POST("suggestions/api/4_1/rs/suggest/address")
    suspend fun suggestAddress(@Body body: DaDataSuggestionsRequest): Response<DaDataSuggestionsResponse>
}