package com.lab.swholocron.data.network

import com.lab.swholocron.data.people.remote.PeopleService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    companion object {
        private const val BASE_URL = "https://swapi.dev/api/"

        private var httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        private var okHttpClient: OkHttpClient
        private var retrofit: Retrofit

        init {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            okHttpClient = OkHttpClient().newBuilder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val peopleService: PeopleService by lazy {
            retrofit.create(
                PeopleService::class.java
            )
        }
    }
}