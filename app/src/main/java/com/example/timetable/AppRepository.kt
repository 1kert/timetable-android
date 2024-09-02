package com.example.timetable

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class AppRepository @Inject constructor() {
    private val apiService = Retrofit.api
    private var allEvents = listOf<TimetableEvent>()
    private val _weekEvents = MutableStateFlow(listOf<List<TimetableEvent>>())
    val weekEvents = _weekEvents.asStateFlow()

    suspend fun fetchTables() {
        try {
            val response = apiService.getTimetable()
            allEvents = response.timetableEvent ?: listOf()
        } catch (e: Exception) {
            println("error fetching")
            e.printStackTrace()
        }

        filterAllTables()
    }

    private fun filterAllTables() {
        println(filterTablesByDay(1))
        val events = mutableListOf<List<TimetableEvent>>()
        for (i in 0..1) events.add(filterTablesByDay(i))
        _weekEvents.value = events.toList()
    }

    private fun filterTablesByDay(days: Int): List<TimetableEvent> {
        return allEvents.filter { event ->
            if (event.date == null) return@filter false

            val localDate = Calendar.getInstance().apply {
                time = Date()
                add(Calendar.DAY_OF_MONTH, days)
            }.time

            val formattedDate = event.date.substring(0, event.date.indexOf('T'))
            val format = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.forLanguageTag("et")
            )

            val eventDate = format.parse(formattedDate) ?: return@filter false

            eventDate.compareTo(format.parse(format.format(localDate))) == 0
        }.sortedBy { it.timeStart }
    }
}
