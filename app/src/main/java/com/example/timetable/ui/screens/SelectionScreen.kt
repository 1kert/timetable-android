package com.example.timetable.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.timetable.R

@Composable
fun SelectionScreen(
    appViewModel: AppViewModel = hiltViewModel(),
    selectionScreenType: SelectionScreenType
) {
}

@Composable
private fun SelectionScreenContent(
    selectionScreenType: SelectionScreenType,
    buttonList: List<Pair<String, String>>
) {
    Column {
        Text (
            text = when(selectionScreenType) {
                SelectionScreenType.ROOM -> stringResource(R.string.select_room)
                SelectionScreenType.TEACHER -> stringResource(R.string.select_teacher)
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        LazyVerticalGrid (
            columns = GridCells.Fixed(2),
            state = rememberLazyGridState()
        ) {
            items(buttonList, key = { it.first }) {

            }
        }
    }
}

enum class SelectionScreenType {
    ROOM,
    TEACHER
}
