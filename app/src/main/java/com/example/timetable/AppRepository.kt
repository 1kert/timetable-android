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

    private val _teacherEvents = MutableStateFlow(listOf<List<TimetableEvent>>())
    val teacherEvents = _teacherEvents.asStateFlow()

    private val _roomEvents = MutableStateFlow(listOf<List<TimetableEvent>>())
    val roomEvents = _roomEvents.asStateFlow()

    suspend fun getTables(url: String) {
        try {
            val response = apiService.fetchTimetable(url)
            val allEvents = response.timetableEvent.orEmpty()
            _weekEvents.value = filterAllTables(allEvents)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getTeacherEvents(uuid: String) {
        try {
            val allEvents = apiService.fetchTeacherEvents(uuid).timetableEvent.orEmpty()
            _teacherEvents.value = filterAllTables(allEvents)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getRoomEvents(uuid: String) {
        try {
            val allEvents = apiService.fetchRoomEvents(uuid).timetableEvent.orEmpty()
            _roomEvents.value = filterAllTables(allEvents)
        } catch (e: Exception) {
            e.printStackTrace()
        }
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

    private fun filterAllTables(allEvents: List<TimetableEvent>): List<List<TimetableEvent>> {
        val relativeDayEvents = sortedMapOf<Long, MutableList<TimetableEvent>>()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag("et"))
        val currentDate = dateFormat.parse(dateFormat.format(Calendar.getInstance().time)) ?: return emptyList()

        allEvents.forEach { event ->
            if (event.date == null) return@forEach

            val eventDateSubStr = event.date.substring(0, event.date.indexOf('T'))
            val eventDate = dateFormat.parse(eventDateSubStr) ?: return@forEach
            val dateDifference = eventDate.time - currentDate.time
            val dayDifference = dateDifference / 86400000
            if (!relativeDayEvents.containsKey(dayDifference))
                relativeDayEvents[dayDifference] = mutableListOf()
            relativeDayEvents[dayDifference]?.add(event)
        }

        return relativeDayEvents.toList().filter { it.first >= 0 }.map { it.second.sortedBy { innerList -> innerList.timeStart } }
    }
}
