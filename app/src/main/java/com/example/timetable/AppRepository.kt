package com.example.timetable

import com.example.timetable.data.Retrofit
import com.example.timetable.data.RoomModel
import com.example.timetable.data.TeacherModel
import com.example.timetable.data.TimetableEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class AppRepository @Inject constructor() {
    private val apiService = Retrofit.api

    private val _weekEvents = MutableStateFlow(listOf<List<TimetableEvent>>())
    val weekEvents = _weekEvents.asStateFlow()

    private val _rooms = MutableStateFlow(listOf<RoomModel>())
    val rooms = _rooms.asStateFlow()

    private val _teachers = MutableStateFlow(listOf<TeacherModel>())
    val teachers = _teachers.asStateFlow()

    private val _teacherEvents = MutableStateFlow(listOf<TimetableEvent>())
    val teacherEvents = _teacherEvents.asStateFlow()

    private val _roomEvents = MutableStateFlow(listOf<TimetableEvent>())
    val roomEvents = _roomEvents.asStateFlow()

    suspend fun getTables(url: String) {
        val allEvents: List<TimetableEvent>
        try {
            val response = apiService.fetchTimetable(url)
            allEvents = response.timetableEvent ?: listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        filterAllTables(allEvents)
    }

    suspend fun getAllRooms() { // todo: sort
        try {
            val response = apiService.fetchAllRooms()
            _rooms.value = response
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getAllTeachers() { // todo: sort
        try {
            val response = apiService.fetchAllTeachers()
            _teachers.value = response
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getTeacherEvents(uuid: String) { // todo: sort
        try {
            val events = apiService.fetchTeacherEvents(uuid).timetableEvent
            _teacherEvents.value = events.orEmpty()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getRoomEvents(uuid: String) { // todo: sort
        try {
            val events = apiService.fetchRoomEvents(uuid).timetableEvent
            _roomEvents.value = events.orEmpty()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun filterAllTables(allEvents: List<TimetableEvent>) {
        val events = mutableListOf<List<TimetableEvent>>()
        for (i in 0..120) { // todo: fix dog shit complexity
            val filteredDay = filterTablesByDay(allEvents = allEvents, days = i)
            if (filteredDay.isNotEmpty()) events.add(filteredDay)
        }
        _weekEvents.value = events.toList()
    }

    private fun filterTablesByDay(
        allEvents: List<TimetableEvent>,
        days: Int
    ): List<TimetableEvent> {
        return allEvents.filter { event ->
            if (event.date == null) return@filter false

            val relativeLocalDate = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_MONTH, days)
            }.time

            val eventDateSubStr = event.date.substring(0, event.date.indexOf('T'))
            val dateFormat = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.forLanguageTag("et")
            )

            val formattedEventDate = dateFormat.format(dateFormat.parse(eventDateSubStr) ?: return@filter false)
            val formattedRelativeDate = dateFormat.format(relativeLocalDate)

            formattedEventDate == formattedRelativeDate
        }.sortedBy { it.timeStart }
    }
}
