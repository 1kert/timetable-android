package com.example.timetable.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.timetable.components.AppTopBar
import com.example.timetable.components.EventsList
import com.example.timetable.data.TimetableEvent
import com.example.timetable.ui.theme.eventHorizontalPadding
import com.example.timetable.ui.theme.eventTopPadding

@Composable
fun HomeScreen(
    uiState: TimetableViewState,
    getDayName: (List<TimetableEvent>) -> String,
    onNextGroup: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        MainAppContent(
            uiState = uiState,
            getDayName = getDayName,
            onNextGroup = onNextGroup
        )
    }
}

@Composable
private fun MainAppContent(
    uiState: TimetableViewState,
    onNextGroup: () -> Unit,
    getDayName: (List<TimetableEvent>) -> String,
) {
    HorizontalPager(
        state = rememberPagerState { uiState.events.size },
        modifier = Modifier.fillMaxSize()
    ) { page ->
        TimetablePage(
            events = uiState.events[page],
            getDayName = getDayName,
            currentGroup = uiState.selectedGroup,
            onNextGroup = onNextGroup
        )
    }
}

@Composable
private fun TimetablePage(
    events: List<TimetableEvent>,
    currentGroup: String,
    getDayName: (List<TimetableEvent>) -> String,
    onNextGroup: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = eventTopPadding)
            .padding(horizontal = eventHorizontalPadding)
    ) {
        AppTopBar(
            dateString = getDayName(events),
            groupTag = currentGroup,
            onSettingsClick = {},
            onNextGroup = onNextGroup
        )

        EventsList(events)
    }
}
