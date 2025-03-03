package com.example.timetable.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SelectionScreen(
    selectionScreenType: SelectionScreenType
) {
    Text(
        text = "current screen: $selectionScreenType"
    )
}

enum class SelectionScreenType {
    ROOM,
    TEACHER
}
