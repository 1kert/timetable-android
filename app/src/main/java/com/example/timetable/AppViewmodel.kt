package com.example.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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
    private val appRepository: AppRepository
): ViewModel() {
    private val apiService = Retrofit.api
    private var timetables: Timetables? = null
    private val _uiState = MutableStateFlow<List<List<TimetableEvent>>>(listOf())
    val uiState = _uiState.asStateFlow()

    init {
        fetchTables()
        observeEvents()
    }

    private fun fetchTables() = viewModelScope.launch { appRepository.fetchTables() }

    private fun observeEvents() {
        viewModelScope.launch {
            appRepository.weekEvents.collect { collected ->
                println(collected)
                _uiState.value = collected
            }
        }
    }
}
