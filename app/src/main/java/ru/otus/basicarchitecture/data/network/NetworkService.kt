package ru.otus.basicarchitecture.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.otus.basicarchitecture.domain.models.NetworkRequest
import ru.otus.basicarchitecture.domain.models.Suggestions

interface NetworkService {

    @POST("address")
    suspend fun sendQuery(@Body text:NetworkRequest): Response<Suggestions>
}