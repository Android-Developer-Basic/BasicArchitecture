package ru.otus.basicarchitecture.model

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
interface AddressDataService {
    @POST("api/4_1/rs/suggest/address")
    @Headers("Content-Type:application/json",
        "Accept:application/json",
        "Authorization:Token 628a6b883336f402594f379b42e80e567ba3a12c")
    suspend fun getAddressHint(@Body query: AddressQuery): AddressData
}
suspend fun callData(service: AddressDataService, query:String):AddressData = suspendCancellableCoroutine { continuation ->
//    val call = service.getAddressHint(AddressQuery(query))
    val callback = object : Callback<AddressData> {
        override fun onResponse(call: Call<AddressData>, response: Response<AddressData>) {
            continuation.resume(response.body()!!)
        }
        override fun onFailure(call: Call<AddressData>, t: Throwable) {
            continuation.resumeWithException(t)
        }
    }
    continuation.invokeOnCancellation {
    }
}