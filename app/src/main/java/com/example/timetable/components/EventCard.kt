package com.example.timetable.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timetable.ui.theme.TimetableTheme

@Composable
fun EventCard(
    name: String?,
    roomCodes: List<String>,
    teachers: List<String>,
    timeStart: String?,
    timeEnd: String?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = name.orEmpty(),
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = roomCodes.joinToString(", "),
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                    Text(
                        text = teachers.joinToString(", "),
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }
                Text(
                    text = "$timeStart - $timeEnd",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium
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
            name = "Tarkvaraarendus",
            roomCodes = "asd asd asd asd asd awdj awjd hawkjdh wakjasd asd asd asd asd asd asd asd".split(' '),
            teachers = listOf("bruh man"),
            timeStart = "15:14",
            timeEnd = "24:00"
        )
    }
}
