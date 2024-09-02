package com.example.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AppViewmodel @Inject constructor(
    appRepository: AppRepository
): ViewModel() {
    private val apiService = Retrofit.api
    private var timetables: Timetables? = null
    private val _uiState = MutableStateFlow<List<TimetableEvent>>(listOf())
    val uiState = _uiState.asStateFlow()

    init {
        getTables()
    }

    private fun getTables() {
        viewModelScope.launch {
            try {
                val response = apiService.getTimetable()
                _uiState.value = response.timetableEvent ?: listOf()
            } catch (e: Exception) {
                println("error fetching")
                e.printStackTrace()
            }

            filterToday()
        }
    }

    private fun filterToday() {
        _uiState.value = _uiState.value.filter { event ->
            if(event.date == null) return@filter false

            val localDate = Date()
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
