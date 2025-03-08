package com.example.timetable.data

import com.google.gson.annotations.SerializedName

data class RoomModel (
    val code: String,
    @SerializedName("roomUuid") val uuid: String
)

data class TeacherModel (
    val firstname: String,
    val lastname: String,
    @SerializedName("teacherUuid") val uuid: String
)
