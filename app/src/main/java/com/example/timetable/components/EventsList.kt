package com.example.timetable.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.timetable.data.TimetableEvent
import com.example.timetable.ui.theme.eventBottomPadding

@Composable
fun EventsList(
    events: List<TimetableEvent>,
    bottomPadding: Dp = eventBottomPadding
) {
    LazyColumn(
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = bottomPadding)
    ) {
        items(events) { event ->
            EventCard(event)
        }
    }
}
