package ru.otus.basicarchitecture.model

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AddressService @Inject constructor(){
    private val client = OkHttpClient()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://suggestions.dadata.ru/suggestions/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val service = retrofit.create(DaDataService::class.java)
      suspend fun request(query:String, callback: (response:AddressData?) -> Unit) {
       val  response = service.getAddressHint(AddressQuery(query))
          if(response.isSuccessful) {
              callback(response.body())
          }
          else {
              Log.e("Ошибочка", "${response.code()} ${response.message()}")
          }
    }
    interface DaDataService {
        @POST("api/4_1/rs/suggest/address")
        @Headers("Content-Type:application/json",
        "Accept:application/json",
        "Authorization:Token 628a6b883336f402594f379b42e80e567ba3a12c")
        suspend fun getAddressHint(@Body query: AddressQuery):Response<AddressData>
    }

}

