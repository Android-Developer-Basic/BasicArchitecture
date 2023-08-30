package ru.otus.basicarchitecture.internetAdrdess

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface AddressService {
    @POST("suggestions/api/4_1/rs/suggest/address")
    suspend fun getAddressAnswer(@Body query: Query): Response<AddressAnswer>

//    @GET("suggestions/api/4_1/rs/suggest/address")
    //abstract fun getAddressAnswerAbstract(@Body query: Query): List<AddressAnswer>
}
//@body - передавать объект query , что в параметрах в body запроса
//Result надо обрабатывать исключение  List не надо
//POST   постоянная часть ссылки - https://suggestions.dadata.ru
// переменная часть ссылки указывается в аннотации - /suggestions/api/4_1/rs/suggest/address
