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

    suspend fun fetchTables(url: String) {
        val allEvents: List<TimetableEvent>
        try {
            val response = apiService.getTimetable(url)
            allEvents = response.timetableEvent ?: listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        filterAllTables(allEvents)
    }

    suspend fun fetchAllRooms() { // todo: sort
        try {
            val response = apiService.getRooms()
            _rooms.value = response
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
    }

    suspend fun fetchAllTeachers() { // todo: sort
        try {
            val response = apiService.getTeachers()
            _teachers.value = response
        } catch (e: Exception) {
            e.printStackTrace()
            return
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
