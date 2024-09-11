package com.example.timetable.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timetable.StudentGroup
import com.example.timetable.Teacher
import com.example.timetable.TimetableEvent
import com.example.timetable.TimetableRoom
import com.example.timetable.ui.theme.TimetableTheme
import com.example.timetable.ui.theme.defaultEventColorDark
import com.example.timetable.ui.theme.onEventColorDark
import com.example.timetable.ui.theme.singleEventColorDark

@Composable
fun EventCard(
    event: TimetableEvent
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors().copy(
            containerColor = if(event.singleEvent) singleEventColorDark else defaultEventColorDark
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = event.name.orEmpty(),
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                color = onEventColorDark
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    val rooms = event.rooms ?: listOf()
                    val studentGroups = event.studentGroups ?: listOf()
                    val teachers = event.teachers ?: listOf()

                    if(rooms.isNotEmpty()) Text(
                        text = rooms.map { it.roomCode }.joinToString(", "),
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = onEventColorDark
                    )
                    if(studentGroups.isNotEmpty()) Text(
                        text = studentGroups.map { it.code }.joinToString(", "),
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = onEventColorDark
                    )
                    if(teachers.isNotEmpty()) Text(
                        text = teachers.map { it.name }.joinToString(", "),
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = onEventColorDark
                    )
                }
                Text(
                    text = "${event.timeStart} - ${event.timeEnd}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = onEventColorDark
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
                teachers = listOf(Teacher("bruh man")),
                timeStart = "15:14",
                timeEnd = "24:00",
                date = null,
                studentGroups = listOf(StudentGroup("TAK-23"), StudentGroup("TA-23")),
                singleEvent = false
            )
        )
    }
}
