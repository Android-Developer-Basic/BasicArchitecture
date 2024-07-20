package ru.otus.basicarchitecture.data


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import ru.otus.basicarchitecture.Core.Model.DTO.SuggestionRequest
import ru.otus.basicarchitecture.Core.Model.DTO.Suggestions
import ru.otus.basicarchitecture.Core.Model.Person
import ru.otus.basicarchitecture.Core.Utils.AppConstant
import ru.otus.basicarchitecture.Network.Endpoints
import javax.inject.Singleton

@Singleton
interface NetworkService {
    @POST(Endpoints.address)
    suspend fun getAddress(@Body body: SuggestionRequest): Response<Suggestions>
}