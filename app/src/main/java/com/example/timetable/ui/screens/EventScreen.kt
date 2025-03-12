package com.example.timetable.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.timetable.data.TimetableEvent

@Composable
fun EventScreen(
    title: String,
    events: List<TimetableEvent>
) {
    Column {
        Text(text = title)
        Text(text = events.joinToString(" ") { it.name.toString() })
    }
}
