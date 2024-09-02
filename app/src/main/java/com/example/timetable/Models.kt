package com.example.timetable

data class Timetables(
    val timetableEvent: List<TimetableEvent>?,
    val studyPeriods: String?
)

data class TimetableEvent(
    val name: String?,
    val date: String?,
    val timeStart: String?,
    val teacher: List<Teacher>?,
    val room: List<TimetableRoom>?,
    val studentGroups: List<StudentGroup>?
)

data class StudentGroup(
    val code: String?
)

data class TimetableRoom(
    val roomCode: String?,
    val buildingCode: String?
)

data class Teacher(
    val name: String?
)
