package ru.otus.basicarchitecture.model

import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

class AddressService {
    private val client = OkHttpClient()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://suggestions.dadata.ru/suggestions/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val service = retrofit.create(DaDataService::class.java)

     fun request(query:String, callback: (response:AddressData?) -> Unit) {
        val call = service.getAddressHint(AddressQuery(query))
        call.enqueue(object: Callback<AddressData> {
            override fun onResponse(call: Call<AddressData>, response: Response<AddressData>) {
                callback(response.body())
            }
            override fun onFailure(call: Call<AddressData>, t: Throwable) {
               // TODO("Not yet implemented")
            }
        })
    }
    interface DaDataService {
        @POST("api/4_1/rs/suggest/address")
        @Headers("Content-Type:application/json",
        "Accept:application/json",
        "Authorization:Token 628a6b883336f402594f379b42e80e567ba3a12c")
         fun getAddressHint(@Body query: AddressQuery):Call<AddressData>
    }

}

