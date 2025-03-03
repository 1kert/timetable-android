package com.example.timetable.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

object Retrofit {
    private const val URL = "https://tahveltp.edu.ee"
    val api: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @GET
    suspend fun getTimetable(@Url url: String): Timetables
}
