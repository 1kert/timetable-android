package com.example.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AppViewmodel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {
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
                _uiState.value = collected
            }
        }
    }

    fun formatDate(dateStr: String?): String {
        if (dateStr == null) return ""
        val locale = Locale.forLanguageTag("et")
        val format = SimpleDateFormat("yyyy-MM-dd", locale)
        val date = format.parse(dateStr) ?: ""

        return SimpleDateFormat("d MMMM", locale).format(date)
    }
}
