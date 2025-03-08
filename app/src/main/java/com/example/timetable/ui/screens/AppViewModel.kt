package com.example.timetable.ui.screens

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.timetable.AppRepository
import com.example.timetable.data.RoomModel
import com.example.timetable.data.TeacherModel
import com.example.timetable.data.TimetableEvent
import com.example.timetable.ui.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {
    private val locale = Locale.forLanguageTag("et")

    private val _timetableState = MutableStateFlow(TimetableViewState())
    val timetableState = _timetableState.asStateFlow()

    private val _roomState = MutableStateFlow(listOf<RoomModel>())
    val roomState = _roomState.asStateFlow()

    private val _teacherState = MutableStateFlow(listOf<TeacherModel>())
    val teacherState = _teacherState.asStateFlow()

    init {
        fetchTables(groups[timetableState.value.selectedGroup].orEmpty())
        viewModelScope.launch { appRepository.fetchAllRooms() }
        viewModelScope.launch { appRepository.fetchAllTeachers() }
        observeEvents()
    }

    private fun fetchTables(groupUuid: String) = viewModelScope.launch { appRepository.fetchTables(groupUuid) }

    private fun observeEvents() {
        combine(
            appRepository.weekEvents,
            appRepository.rooms,
            appRepository.teachers
        ) { events, rooms, teachers ->
            Triple(events, rooms, teachers)
        }.onEach { collected ->
            _timetableState.update { it.copy(events = collected.first) }
            _roomState.value = collected.second
            _teacherState.value = collected.third
        }.launchIn(viewModelScope)
    }

    fun onNextGroup() {
        var current = timetableState.value.selectedGroup
        val availableGroups = groups.keys.toList()
        var newIndex = availableGroups.indexOf(current) + 1
        if (newIndex >= availableGroups.size) newIndex = 0
        current = availableGroups[newIndex]
        fetchTables(groups[current].orEmpty())
        _timetableState.update { it.copy(selectedGroup = current) }
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

    fun navigate(navHostController: NavHostController, navigationRoute: NavigationRoute) {
        _timetableState.update {
            it.copy(currentNavigation = navigationRoute)
        }
        navHostController.navigate(navigationRoute)
    }
}

data class TimetableViewState(
    val events: List<List<TimetableEvent>> = listOf(),
    val selectedGroup: String = "ta23",
    val currentNavigation: NavigationRoute = NavigationRoute.StudentScreen
)

private val groups = mapOf(
    "ta23" to "50b463db-a5d9-484d-a028-b7c77344d038",
    "tak23" to "e3f24c2e-ed4a-49b8-92db-7781c3498c93",
    "tak24" to "4b26d1e5-11ac-4c63-840e-46c450c529ee"
)
