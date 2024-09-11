package com.example.timetable

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

private const val ta23 = "hois_back/schoolBoard/38/timetableByGroup?lang=ET&studentGroups=7597"
private const val tak23 = "hois_back/schoolBoard/38/timetableByGroup?lang=ET&studentGroups=7596"
private const val tak24 = "hois_back/schoolBoard/38/timetableByGroup?lang=ET&studentGroups=9329"

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
    @GET
    suspend fun getTimetable(@Url url: String): Timetables
}
