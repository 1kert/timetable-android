package com.example.timetable

import com.example.timetable.data.Retrofit
import com.example.timetable.data.TimetableEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class AppRepository @Inject constructor() {
    private val apiService = Retrofit.api
    private var allEvents = listOf<TimetableEvent>()
    private val _weekEvents = MutableStateFlow(listOf<List<TimetableEvent>>())
    val weekEvents = _weekEvents.asStateFlow()

    suspend fun fetchTables(url: String) {
        try {
            val response = apiService.getTimetable(url)
            allEvents = response.timetableEvent ?: listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        filterAllTables(allEvents)
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
