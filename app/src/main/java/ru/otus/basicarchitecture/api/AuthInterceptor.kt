package ru.otus.basicarchitecture.api

import okhttp3.Interceptor
import okhttp3.Response
import ru.otus.basicarchitecture.BuildConfig

const val API_KEY = BuildConfig.DADATA_API_KEY

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestWithAuth = request.newBuilder()
            .header("Authorization", "Token $API_KEY")
            .build()

        return chain.proceed(requestWithAuth)
    }
}