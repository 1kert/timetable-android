package com.example.timetable.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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
    @GET("hois_back/schoolBoard/38/timetableByGroup?lang=ET")
    suspend fun fetchTimetable(@Query("studentGroupUuid") uuid: String): Timetables

    @GET("/hois_back/schoolBoard/38/room/timetables")
    suspend fun fetchAllRooms(): List<RoomModel>

    @GET("/hois_back/schoolBoard/38/teacher/timetables")
    suspend fun fetchAllTeachers(): List<TeacherModel>

    @GET("/hois_back/schoolBoard/38/timetableByTeacher?lang=ET")
    suspend fun fetchTeacherEvents(@Query("teacherUuid") uuid: String): Timetables

    @GET("/hois_back/schoolBoard/38/timetableByRoom?lang=ET")
    suspend fun fetchRoomEvents(@Query("roomUuid") uuid: String): Timetables
}
