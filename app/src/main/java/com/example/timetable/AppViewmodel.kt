package com.example.timetable

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AppViewmodel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {
    private val locale = Locale.forLanguageTag("et")
    private val _uiState = MutableStateFlow(TimetableViewState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchTables(groups[uiState.value.selectedGroup].orEmpty())
        observeEvents()
    }

    private fun fetchTables(url: String) = viewModelScope.launch { appRepository.fetchTables(url) }

    private fun observeEvents() {
        viewModelScope.launch {
            appRepository.weekEvents.collect { collected ->
                _uiState.update {
                    it.copy(events = collected)
                }
            }
        }
    }

    fun onNextGroup() {
        var current = uiState.value.selectedGroup
        val keys = groups.keys.toList()
        var index = keys.indexOf(current) + 1
        if (index >= keys.size) index = 0
        current = keys[index]
        fetchTables(groups[current].orEmpty())
        _uiState.update { it.copy(selectedGroup = current) }
    }

    fun getDayName(events: List<TimetableEvent>): String {
        val format = SimpleDateFormat("yyyy-MM-dd", locale)
        val eventDateStr = events[0].date!!
        val eventDate = Calendar.getInstance().apply {
            time = format.parse(eventDateStr.substring(0, eventDateStr.indexOf('T')))
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val date = Calendar.getInstance().apply {
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val formattedDate = SimpleDateFormat("d. MMMM", locale).format(eventDate.time)

        if (eventDate.compareTo(date) == 0) return "Täna ($formattedDate)"
        if (eventDate.compareTo(date.apply { add(Calendar.DAY_OF_MONTH, 1) }) == 0) return "Homme ($formattedDate)"
        return formattedDate
    }
}

data class TimetableViewState(
    val events: List<List<TimetableEvent>> = listOf(),
    val selectedGroup: String = "ta23"
)

private val groups = mapOf(
    "ta23" to "hois_back/schoolBoard/38/timetableByGroup?lang=ET&studentGroups=7597",
    "tak23" to "hois_back/schoolBoard/38/timetableByGroup?lang=ET&studentGroups=7596",
    "tak24" to "hois_back/schoolBoard/38/timetableByGroup?lang=ET&studentGroups=9329"
)
