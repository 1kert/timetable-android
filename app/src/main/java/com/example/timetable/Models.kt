package com.example.timetable

import com.google.gson.annotations.SerializedName

data class Timetables(
    @SerializedName("timetableEvents") val timetableEvent: List<TimetableEvent>?
)

data class TimetableEvent(
    @SerializedName("nameEt") val name: String?,
    val date: String?,
    val timeStart: String?,
    val timeEnd: String?,
    val teachers: List<Teacher>?,
    val rooms: List<TimetableRoom>?,
    val studentGroups: List<StudentGroup>?,
    val singleEvent: Boolean
)

data class StudentGroup(
    val code: String?
)

data class TimetableRoom(
    val roomCode: String?
)

data class Teacher(
    val name: String?
)
