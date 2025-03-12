package com.example.timetable.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timetable.R
import com.example.timetable.data.RoomModel
import com.example.timetable.data.TeacherModel
import com.example.timetable.ui.theme.TimetableTheme
import com.example.timetable.ui.theme.roomInfoCard
import com.example.timetable.ui.theme.teacherInfoCard

@Composable
fun SelectionScreen(
    roomState: List<RoomModel>,
    teacherState: List<TeacherModel>,
    selectionScreenType: SelectionScreenType,
    onInfoCardClick: (SelectionScreenType, uuid: String, title: String) -> Unit
) {
    val list = when (selectionScreenType) {
        SelectionScreenType.ROOM -> roomState.map { Pair(it.code, it.uuid) }
        SelectionScreenType.TEACHER -> teacherState.map { Pair("${it.firstname} ${it.lastname}", it.uuid) }
    }

    SelectionScreenContent(
        selectionScreenType = selectionScreenType,
        onClick = onInfoCardClick,
        buttonList = list
    )
}

@Composable
private fun SelectionScreenContent(
    selectionScreenType: SelectionScreenType,
    buttonList: List<Pair<String, String>>,
    onClick: (SelectionScreenType, uuid: String, title: String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = when (selectionScreenType) {
                SelectionScreenType.ROOM -> stringResource(R.string.select_room)
                SelectionScreenType.TEACHER -> stringResource(R.string.select_teacher)
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = rememberLazyGridState(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(buttonList, key = { it.first }) {
                InfoCard(
                    text = it.first,
                    onClick = { onClick(selectionScreenType, it.second, it.first) },
                    selectionScreenType = selectionScreenType
                )
            }
        }
    }
}

@Composable
private fun InfoCard(
    text: String,
    selectionScreenType: SelectionScreenType,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = when (selectionScreenType) {
                SelectionScreenType.ROOM -> roomInfoCard
                SelectionScreenType.TEACHER -> teacherInfoCard
            }
        ),
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Preview
@Composable
private fun InfoCardPreview() {
    TimetableTheme {
        InfoCard(
            text = "some text here",
            onClick = {},
            selectionScreenType = SelectionScreenType.ROOM
        )
    }
}

@Preview
@Composable
private fun SelectionScreenPreview() {
    TimetableTheme {
        SelectionScreenContent(
            selectionScreenType = SelectionScreenType.ROOM,
            buttonList = List(20) {
                Pair("roomcode $it", "")
            },
            onClick = { _, _, _-> }
        )
    }
}

enum class SelectionScreenType {
    ROOM,
    TEACHER
}
