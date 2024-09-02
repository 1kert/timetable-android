package com.example.timetable

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object Retrofit {
    private const val URL = "https://tahvel.edu.ee"
    val api: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @GET("hois_back/schoolBoard/38/timetableByGroup?lang=ET&studentGroups=7597")
    suspend fun getTimetable(): Timetables
}
