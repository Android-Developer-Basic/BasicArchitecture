package ru.otus.basicarchitecture.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DadataApiService {
    private const val BASE_URL = "https://suggestions.dadata.ru/"
    private const val API_KEY = "8fa10df494b20be347e86fb921643f852a13cc07" // Ваш API ключ Dadata

    private val client: OkHttpClient by lazy {
        // Создаем и настраиваем HttpLoggingInterceptor
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Создаем Interceptor для добавления заголовка авторизации
        val headerInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Token $API_KEY")
                .build()
            chain.proceed(request)
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)  // Интерсептор логов
            .addInterceptor(headerInterceptor)   // Интерсептор заголовков
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    val api: DadataApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DadataApi::class.java)
    }
}
