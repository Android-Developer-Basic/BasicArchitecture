package ru.otus.basicarchitecture.network.dadata

import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class DaDataAuthInterceptor @Inject constructor(
    @DaDataApiKey private val apiKey: String,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .apply {
                if (apiKey.isNotBlank()) {
                    header("Authorization", "Token $apiKey")
                }
            }
            .build()

        return chain.proceed(request)
    }
}