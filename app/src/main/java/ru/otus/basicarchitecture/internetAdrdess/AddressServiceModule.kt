package ru.otus.basicarchitecture.internetAdrdess

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AddressServiceModule {

    private fun createClient() = OkHttpClient.Builder()       //через это приложение идет в сеть?
        .addInterceptor(AuthInterceptor())                    //добавляем отслеживалку запросов?
        .build()


    fun retrofitService() = Retrofit.Builder()                //а это тогда что?
        .client(createClient())                               //проброска клиента
        .baseUrl(BASE_URL)                                    //проброска адреса
        .addConverterFactory(GsonConverterFactory.create())   //конвертор фабрика для работы с Json Просто преобразователь класс-текст-класс
        .build()
        .create(AddressService::class.java)

    companion object {
        private const val BASE_URL = "https://suggestions.dadata.ru/"    //основная часть адреса
    }

}