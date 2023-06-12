package ru.otus.basicarchitecture.data

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", "Token $API_KEY")
            .build()
        return chain.proceed(authenticatedRequest)
    }

    companion object {
        private const val API_KEY = "dba189468f0629670436189c23fc38e983096db8"
    }
}