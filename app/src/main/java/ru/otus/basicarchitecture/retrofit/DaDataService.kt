package ru.otus.basicarchitecture.retrofit

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
class AddressService @Inject constructor() {

    private val client = OkHttpClient()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://suggestions.dadata.ru/suggestions/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val service = retrofit.create(DaDataService::class.java)

    suspend fun request(query: String, callback: (response: LocationData?) -> Unit) {
        val response = service.getAddressHint(LocationQuery(query))
        if (response.isSuccessful) {
            callback(response.body())
        } else {
            Log.e("RETROFIT", "${response.code()} - ${response.message()}")
        }
    }

    interface DaDataService {
        @POST("api/4_1/rs/suggest/address")
        @Headers(
            "Content-Type:application/json",
            "Accept:application/json",
            "Authorization:Token 4fd4db69fe46f3cab23abb384713245275c4f136"
        )
        suspend fun getAddressHint(@Body query: LocationQuery): Response<LocationData>
    }
}