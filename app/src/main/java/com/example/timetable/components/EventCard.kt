package com.example.timetable.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timetable.data.StudentGroup
import com.example.timetable.data.Teacher
import com.example.timetable.data.TimetableEvent
import com.example.timetable.data.TimetableRoom
import com.example.timetable.ui.theme.*

@Composable
fun EventCard(
    event: TimetableEvent
) {
    val rooms = event.rooms ?: listOf()
    val studentGroups = event.studentGroups ?: listOf()
    val teachers = event.teachers ?: listOf()
    val columnSpacing = 2.dp

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors().copy(
            containerColor = if (event.singleEvent) singleEventColorDark else defaultEventColorDark
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(columnSpacing),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = event.name.orEmpty(),
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                color = onEventColorPrimaryDark
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(columnSpacing)
                ) {
                    Text(
                        text = "${event.timeStart} - ${event.timeEnd}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = onEventColorPrimaryDark
                    )
                    if (studentGroups.isNotEmpty()) Text(
                        text = studentGroups.map { it.code }.joinToString(", "),
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = onEventColorSecondaryDark
                    )
                    if (teachers.isNotEmpty()) Text(
                        text = teachers.map { it.name }.joinToString(", "),
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = onEventColorSecondaryDark
                    )
                }
                if (rooms.isNotEmpty()) Text(
                    text = rooms.map { it.roomCode }.joinToString(", "),
                    style = MaterialTheme.typography.headlineSmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    color = onEventColorPrimaryDark,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Preview
@Composable
private fun EventCardPreview() {
    TimetableTheme {
        EventCard(
            event = TimetableEvent(
                name = "Tarkvaraarendus",
                rooms = listOf(TimetableRoom(roomCode = "K-202")),
                teachers = listOf(Teacher("Gen Trimmer")),
                timeStart = "15:14",
                timeEnd = "24:00",
                date = null,
                studentGroups = listOf(StudentGroup("TAK-23"), StudentGroup("TA-23")),
                singleEvent = false
            )
        )
    }
}

@Preview
@Composable
private fun EventCardSingleEventPreview() {
    TimetableTheme {
        EventCard(
            event = TimetableEvent(
                name = "Tarkvaraarendus",
                rooms = listOf(TimetableRoom(roomCode = "K-202")),
                teachers = listOf(Teacher("bruh man")),
                timeStart = "15:14",
                timeEnd = "24:00",
                date = null,
                studentGroups = listOf(StudentGroup("TAK-23"), StudentGroup("TA-23")),
                singleEvent = true
            )
        )
    }
}

